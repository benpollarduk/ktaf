package com.github.benpollarduk.ktaf.logic

import com.github.benpollarduk.ktaf.assets.Examinable
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.interaction.InteractWithItem
import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.assets.locations.Overworld
import com.github.benpollarduk.ktaf.assets.locations.ViewPoint
import com.github.benpollarduk.ktaf.conversations.Converser
import com.github.benpollarduk.ktaf.extensions.equalsExaminable
import com.github.benpollarduk.ktaf.extensions.preen
import com.github.benpollarduk.ktaf.interpretation.CharacterCommandInterpreter
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import com.github.benpollarduk.ktaf.interpretation.ConversationCommandInterpreter
import com.github.benpollarduk.ktaf.interpretation.CustomCommandInterpreter
import com.github.benpollarduk.ktaf.interpretation.FrameCommandInterpreter
import com.github.benpollarduk.ktaf.interpretation.GlobalCommandInterpreter
import com.github.benpollarduk.ktaf.interpretation.InputInterpreter
import com.github.benpollarduk.ktaf.interpretation.Interpreter
import com.github.benpollarduk.ktaf.interpretation.ItemCommandInterpreter
import com.github.benpollarduk.ktaf.interpretation.MovementCommandInterpreter
import com.github.benpollarduk.ktaf.io.IOConfiguration
import com.github.benpollarduk.ktaf.io.configurations.AnsiConsoleConfiguration
import com.github.benpollarduk.ktaf.logic.conditions.EndCheck
import com.github.benpollarduk.ktaf.rendering.KeyType
import com.github.benpollarduk.ktaf.rendering.frames.Frame

/**
 * Provides the overall container and logic for a game.
 */
public class Game(
    public val information: GameInformation,
    initialPlayer: PlayableCharacter,
    public val overworld: Overworld,
    private val completionCondition: EndCheck,
    private val gameOverCondition: EndCheck,
    private val errorPrefix: String = DEFAULT_ERROR_PREFIX,
    private val interpreter: Interpreter = defaultInterpreters,
    private val ioConfiguration: IOConfiguration = AnsiConsoleConfiguration
) {
    private var state: GameState = GameState.NOT_STARTED
    public var isExecuting: Boolean = false
        private set
    private val cancellationToken = CancellationToken()
    private var currentFrame: Frame = ioConfiguration.frameBuilders.aboutFrameBuilder.build(
        information.name,
        information.description,
        information.author
    )

    /**
     * The current [PlayableCharacter].
     */
    public var player: PlayableCharacter
        private set

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

    init {
        player = initialPlayer
    }

    /**
     * Display a transition frame with a specified [title] and [message].
     */
    public fun displayTransition(title: String, message: String) {
        refresh(ioConfiguration.frameBuilders.transitionFrameBuilder.build(title, message))
    }

    /**
     * Find an [InteractWithItem] within this [Game] from a specified [name]. If the target cannot be found null is
     * returned.
     */
    public fun findInteractionTarget(name: String): InteractWithItem? {
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
    public fun getAllPlayerVisibleExaminables(): List<com.github.benpollarduk.ktaf.assets.Examinable> {
        val examinables = mutableListOf<com.github.benpollarduk.ktaf.assets.Examinable>(player, overworld)
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

    /**
     * Start a conversation with a [converser].
     */
    internal fun startConversation(converser: Converser) {
        activeConverser = converser
        activeConverser?.conversation?.next(this)
    }

    /**
     * End the conversation with the [activeConverser].
     */
    internal fun endConversation() {
        activeConverser = null
    }

    /**
     * Display the about frame.
     */
    internal fun displayAbout() {
        refresh(
            ioConfiguration.frameBuilders.aboutFrameBuilder.build(
                "About",
                this.information.description,
                this.information.author
            )
        )
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
                commands.distinct()
            )
        )
    }

    /**
     * Display the map frame.
     */
    internal fun displayMap() {
        val region = overworld.currentRegion

        if (region != null) {
            refresh(ioConfiguration.frameBuilders.regionMapFrameBuilder.build(region))
        } else {
            refresh(getFallbackFrame())
        }
    }

    private fun checkForCompletion() {
        val completionCheckResult = completionCondition(this)
        if (completionCheckResult.conditionMet) {
            waitForInput()
            refresh(
                ioConfiguration.frameBuilders.completionFrameBuilder.build(
                    completionCheckResult.title,
                    completionCheckResult.description
                )
            )
            waitForInput()
            end()
        }
    }

    private fun checkForGameOver() {
        val gameOverCheckResult = gameOverCondition(this)
        if (gameOverCheckResult.conditionMet) {
            waitForInput()
            refresh(
                ioConfiguration.frameBuilders.gameOverFrameBuilder.build(
                    gameOverCheckResult.title,
                    gameOverCheckResult.description
                )
            )
            waitForInput()
            end()
        }
    }

    private fun actionConversation(converser: Converser?) {
        if (converser != null) {
            refresh(
                ioConfiguration.frameBuilders.conversationFrameBuilder.build(
                    "Conversation with ${converser.identifier.name}",
                    converser,
                    interpreter.getContextualCommandHelp(this)
                )
            )
        }
    }

    private fun waitForInput(): String {
        if (!currentFrame.acceptsInput) {
            val frame = currentFrame
            while (!ioConfiguration.waitForAcknowledge(cancellationToken) &&
                !cancellationToken.wasCancelled &&
                currentFrame == frame
            ) {
                drawFrame(currentFrame)
            }
            return ""
        } else {
            return ioConfiguration.waitForCommand(cancellationToken)
        }
    }

    private fun interpretInput(input: String): Reaction {
        val interpretation = interpreter.interpret(input, this)

        return when {
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

    private fun handleReaction(reaction: Reaction) {
        when (reaction.result) {
            ReactionResult.ERROR -> {
                refresh("$errorPrefix: ${reaction.description}")
            }
            ReactionResult.OK -> {
                refresh(reaction.description)
            }
            ReactionResult.INTERNAL -> {
                // nothing
            }
            ReactionResult.FATAL -> {
                refresh(reaction.description)
            }
        }
    }

    internal fun execute() {
        if (isExecuting) {
            return
        }

        isExecuting = true

        refresh(
            ioConfiguration.frameBuilders.titleFrameBuilder.build(
                information.name,
                information.introduction
            )
        )

        var input = ""
        var reaction = Reaction(ReactionResult.ERROR, "Error.")

        do {
            var displayReactionToInput = true

            actionConversation(activeConverser)

            if (state == GameState.ACTIVE) {
                input = waitForInput()
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
                        reaction = interpretInput(input)
                    }
                }
                GameState.FINISHED -> {
                    displayReactionToInput = false
                }
            }

            if (reaction.result == ReactionResult.FATAL) {
                player.kill()
            }

            if (displayReactionToInput) {
                handleReaction(reaction)
            }

            checkForCompletion()
            checkForGameOver()
        } while (state != GameState.FINISHED)

        isExecuting = false
    }

    /**
     * Enter the [Game].
     */
    private fun enter() {
        state = GameState.ACTIVE
    }

    /**
     * End the [Game].
     */
    internal fun end() {
        cancellationToken.cancel()
        state = GameState.FINISHED
    }

    /**
     * Change to a specified [player].
     */
    public fun changePlayer(player: PlayableCharacter) {
        this.player = player
    }

    private fun getFallbackFrame(): Frame {
        return ioConfiguration.frameBuilders.transitionFrameBuilder.build(
            "Error",
            "Couldn't refresh frame."
        )
    }

    private fun drawFrame(frame: Frame) {
        frame.render(ioConfiguration.renderFrame)
    }

    private fun refresh(message: String = "") {
        val region = overworld.currentRegion
        val room = region?.currentRoom

        if (room != null) {
            refresh(
                ioConfiguration.frameBuilders.sceneFrameBuilder.build(
                    room,
                    ViewPoint(region),
                    player,
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

    public companion object {
        private const val DEFAULT_ERROR_PREFIX: String = "Oops"

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
    }
}
