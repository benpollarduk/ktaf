package ktaf.logic

import ktaf.assets.Examinable
import ktaf.assets.Size
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.interaction.InteractionTarget
import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.assets.locations.Overworld
import ktaf.assets.locations.ViewPoint
import ktaf.conversations.Converser
import ktaf.extensions.equalsExaminable
import ktaf.extensions.preen
import ktaf.interpretation.CharacterCommandInterpreter
import ktaf.interpretation.CommandHelp
import ktaf.interpretation.ConversationCommandInterpreter
import ktaf.interpretation.CustomCommandInterpreter
import ktaf.interpretation.FrameCommandInterpreter
import ktaf.interpretation.GlobalCommandInterpreter
import ktaf.interpretation.InputInterpreter
import ktaf.interpretation.Interpreter
import ktaf.interpretation.ItemCommandInterpreter
import ktaf.interpretation.MovementCommandInterpreter
import ktaf.io.IOConfiguration
import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.rendering.FramePosition
import ktaf.rendering.KeyType
import ktaf.rendering.frames.Frame
import java.util.*

/**
 * Provides the overall container and logic for a game.
 */
public class Game private constructor(
    public val name: String,
    public val introduction: String,
    public val description: String,
    public val author: String,
    public val player: PlayableCharacter,
    public val overworld: Overworld,
    public val frameSize: Size,
    private val completionCondition: EndCheck,
    private val gameOverCondition: EndCheck,
    private val exitMode: ExitMode,
    private val errorPrefix: String,
    private val interpreter: Interpreter,
    private val ioConfiguration: IOConfiguration
) {
    /**
     * The active [Converser].
     */
    public var activeConverser: Converser? = null
        private set

    /**
     * Specifies if the command list should be displayed in scene frames.
     */
    public var displayCommandListInSceneFrames: Boolean = true

    /**
     * Specifies the type of key to use for scene maps.
     */
    public var sceneMapKeyType: KeyType = KeyType.DYNAMIC

    /**
     * Determines if this [Game] is executing.
     */
    private var isExecuting: Boolean = false

    private var currentFrame: Frame = ioConfiguration.frameBuilders.aboutFrameBuilder.build("", this)
    private var state: GameState = GameState.NOT_STARTED
    private val startingFrameDrawCallbacks: MutableList<FrameDraw> = mutableListOf()
    private val finishedFrameDrawCallbacks: MutableList<FrameDraw> = mutableListOf()

    /**
     * Start listening to StartingFrameDraw.
     */
    public fun subscribeToStartingFrameDraw(listener: FrameDraw) {
        startingFrameDrawCallbacks.add(listener)
    }

    /**
     * Start listening to StartingFrameDraw.
     */
    public fun subscribeToFinishedFrameDraw(listener: FrameDraw) {
        finishedFrameDrawCallbacks.add(listener)
    }

    /**
     * Stop listening to StartingFrameDraw.
     */
    public fun unsubscribeFromStartingFrameDraw(listener: FrameDraw) {
        startingFrameDrawCallbacks.remove(listener)
    }

    /**
     * Stop listening to StartingFrameDraw.
     */
    public fun unsubscribeFromFinishedFrameDraw(listener: FrameDraw) {
        finishedFrameDrawCallbacks.remove(listener)
    }

    /**
     * Start a conversation with a [converser].
     */
    internal fun startConversation(converser: Converser) {
        activeConverser = converser
        activeConverser?.conversation?.next(this)
    }

    /**
     * End the [Conversation] with the [activeConverser].
     */
    internal fun endConversation() {
        activeConverser = null
    }

    /**
     * Display the about frame.
     */
    internal fun displayAbout() {
        refresh(ioConfiguration.frameBuilders.aboutFrameBuilder.build("About", this))
    }

    /**
     * Display the help frame.
     */
    internal fun displayHelp() {
        val commands: MutableList<CommandHelp> = mutableListOf()
        commands.addAll(interpreter.supportedCommands)
        commands.addAll(interpreter.getContextualCommandHelp(this))
        refresh(
            ioConfiguration.frameBuilders.helpFrameBuilder.build(
                "Help",
                "",
                commands.distinct(),
                this
            )
        )
    }

    /**
     * Display the map frame.
     */
    internal fun displayMap() {
        val region = overworld.currentRegion

        if (region != null) {
            refresh(ioConfiguration.frameBuilders.regionMapFrameBuilder.build(region, this))
        } else {
            refresh(getFallbackFrame())
        }
    }

    /**
     * Display a transition frame with a specified [title] and [message].
     */
    public fun displayTransition(title: String, message: String) {
        refresh(ioConfiguration.frameBuilders.transitionFrameBuilder.build(title, message, this))
    }

    private fun execute() {
        if (isExecuting) {
            return
        }

        isExecuting = true

        refresh(ioConfiguration.frameBuilders.titleFrameBuilder.build(this))

        var input = ""
        var reaction = Reaction(ReactionResult.ERROR, "Error.")

        do {
            var displayReactionToInput = true
            var completionCheckResult = completionCondition(this)
            var gameOverCheckResult = gameOverCondition(this)
            val converser = activeConverser

            when {
                completionCheckResult.conditionMet -> {
                    refresh(
                        ioConfiguration.frameBuilders.completionFrameBuilder.build(
                            completionCheckResult.title,
                            completionCheckResult.description,
                            this
                        )
                    )
                    end()
                }
                gameOverCheckResult.conditionMet -> {
                    refresh(
                        ioConfiguration.frameBuilders.gameOverFrameBuilder.build(
                            gameOverCheckResult.title,
                            gameOverCheckResult.description,
                            this
                        )
                    )
                    end()
                }
                converser != null -> {
                    refresh(
                        ioConfiguration.frameBuilders.conversationFrameBuilder.build(
                            "Conversation with ${converser.identifier.name}",
                            interpreter.getContextualCommandHelp(this),
                            this
                        )
                    )
                }
            }

            if (!currentFrame.acceptsInput) {
                val frame = currentFrame
                while (!ioConfiguration.waitForAcknowledge() && currentFrame == frame) {
                    drawFrame(currentFrame)
                }
            } else {
                input = ioConfiguration.waitForCommand()
            }

            when (state) {
                GameState.NOT_STARTED -> {
                    enter()
                    displayReactionToInput = false
                }
                GameState.ACTIVE -> {
                    if (!currentFrame.acceptsInput) {
                        refresh()
                        displayReactionToInput = false
                    } else {
                        input = input.preen()
                        val interpretation = interpreter.interpret(input, this)

                        reaction = when {
                            interpretation.interpretedSuccessfully -> {
                                interpretation.command.invoke(this)
                            }
                            input.isNotEmpty() -> {
                                Reaction(ReactionResult.ERROR, "$input was not valid input.")
                            }
                            else -> {
                                Reaction(ReactionResult.OK, "")
                            }
                        }
                    }
                }
                GameState.FINISHED -> {
                    displayReactionToInput = false
                }
            }

            if (displayReactionToInput) {
                when (reaction.result) {
                    ReactionResult.ERROR -> {
                        val message = "$errorPrefix: ${reaction.description}"
                        refresh(message)
                    }
                    ReactionResult.OK -> {
                        refresh(reaction.description)
                    }
                    ReactionResult.INTERNAL -> {
                        // nothing
                    }
                    ReactionResult.FATAL -> {
                        // nothing
                    }
                }
            }
        } while (state != GameState.FINISHED)

        isExecuting = false
    }

    /**
     * Enter the [Game].
     */
    private fun enter() {
        state = GameState.ACTIVE
        refresh()
    }

    /**
     * End the [Game].
     */
    internal fun end() {
        state = GameState.FINISHED
    }

    private fun getFallbackFrame(): Frame {
        return ioConfiguration.frameBuilders.transitionFrameBuilder.build(
            "Error",
            "Couldn't refresh frame.",
            this
        )
    }

    private fun drawFrame(frame: Frame) {
        try {
            startingFrameDrawCallbacks.forEach { it.invoke(frame) }
            frame.render(ioConfiguration.displayTextOutput)
            finishedFrameDrawCallbacks.forEach { it.invoke(frame) }
        } catch (e: Exception) {
            println("An exception was caught drawing the frame: ${e.message}")
        }
    }

    private fun refresh(message: String = "") {
        val region = overworld.currentRegion
        val room = region?.currentRoom

        if (room != null) {
            refresh(
                ioConfiguration.frameBuilders.sceneFrameBuilder.build(
                    room,
                    ViewPoint(region),
                    this,
                    message,
                    if (displayCommandListInSceneFrames) interpreter.getContextualCommandHelp(this) else emptyList(),
                    sceneMapKeyType
                )
            )
        } else {
            refresh(getFallbackFrame())
        }
    }

    private fun refresh(frame: Frame) {
        currentFrame = frame
        drawFrame(frame)
    }

    /**
     * Find [InteractionTarget] within this [Game] from a specified [name]. If the target cannot be found null is
     * returned.
     */
    public fun findInteractionTarget(name: String): InteractionTarget? {
        if (name.equalsExaminable(player)) {
            return player
        }

        if (player.items.any { name.equalsExaminable(it) }) {
            return player.findItem(name)
        }

        if (name.equalsExaminable(overworld.currentRegion?.currentRoom)) {
            return overworld.currentRegion?.currentRoom
        }

        return overworld.currentRegion?.currentRoom?.findInteractionTarget(name)
    }

    /**
     * Get a list of all [Examinable] that are currently visible to the player.
     */
    public fun getAllPlayerVisibleExaminables(): List<Examinable> {
        val examinables = mutableListOf<Examinable>(player, overworld)
        val currentRegion = overworld.currentRegion
        val currentRoom = overworld.currentRegion?.currentRoom

        if (currentRegion != null) {
            examinables.add(currentRegion)
        }

        if (currentRoom != null) {
            examinables.add(currentRoom)
        }

        examinables.addAll(player.items.filter { it.isPlayerVisible })

        if (currentRoom != null) {
            examinables.addAll(currentRoom.items.filter { it.isPlayerVisible })
            examinables.addAll(currentRoom.characters.filter { it.isPlayerVisible })
            examinables.addAll(currentRoom.exits.filter { it.isPlayerVisible })
        }

        return examinables.toList()
    }

    public companion object {
        private val defaultSize: Size = Size(80, 50)

        /**
         * Get the default prefix to use for errors.
         */
        public val defaultErrorPrefix: String = "Oops"

        /**
         * Get all default interpreters.
         */
        public val defaultInterpreters: Interpreter = InputInterpreter(
            listOf(
                FrameCommandInterpreter(),
                GlobalCommandInterpreter(),
                ConversationCommandInterpreter(),
                ItemCommandInterpreter(),
                CharacterCommandInterpreter(),
                MovementCommandInterpreter(),
                CustomCommandInterpreter()
            )
        )

        /**
         * Create a new instance of a [GameCreator].
         */
        public fun create(
            name: String,
            introduction: String,
            description: String,
            author: String,
            overworldCreator: OverworldCreator,
            playerCreator: PlayableCharacterCreation,
            completionCondition: EndCheck,
            gameOverCondition: EndCheck,
            frameSize: Size = defaultSize,
            exitMode: ExitMode = ExitMode.RETURN_TO_TITLE_SCREEN,
            errorPrefix: String = defaultErrorPrefix,
            interpreter: Interpreter = defaultInterpreters,
            ioConfiguration: IOConfiguration = AnsiConsoleConfiguration
        ): GameCreator {
            val player = playerCreator()
            return {
                Game(
                    name,
                    introduction,
                    description,
                    author,
                    player,
                    overworldCreator(player),
                    frameSize,
                    completionCondition,
                    gameOverCondition,
                    exitMode,
                    errorPrefix,
                    interpreter,
                    ioConfiguration
                )
            }
        }

        /**
         * Execute an instance of a [GameCreator].
         */
        public fun execute(creator: GameCreator) {
            var run = true

            while (run) {
                val game = creator.invoke()

                game.subscribeToStartingFrameDraw {
                    game.ioConfiguration.clearOutput()
                }
                game.subscribeToFinishedFrameDraw {
                    game.ioConfiguration.adjustInput(
                        it.acceptsInput,
                        FramePosition(it.cursorLeft, it.cursorTop)
                    )
                }

                game.execute()

                when (game.exitMode) {
                    ExitMode.EXIT_APPLICATION -> {
                        run = false
                    }
                    ExitMode.RETURN_TO_TITLE_SCREEN -> {
                        // nothing
                    }
                }
            }
        }
    }
}
