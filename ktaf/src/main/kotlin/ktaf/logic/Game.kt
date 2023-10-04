package ktaf.logic

import ktaf.assets.Examinable
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
import ktaf.logic.conditions.EndCheck
import ktaf.rendering.KeyType
import ktaf.rendering.frames.Frame

/**
 * Provides the overall container and logic for a game.
 */
public class Game(
    public val information: GameInformation,
    public val player: PlayableCharacter,
    public val overworld: Overworld,
    private val completionCondition: EndCheck,
    private val gameOverCondition: EndCheck,
    private val errorPrefix: String = DEFAULT_ERROR_PREFIX,
    private val interpreter: Interpreter = defaultInterpreters,
    private val ioConfiguration: IOConfiguration = AnsiConsoleConfiguration,
) {
    private var state: GameState = GameState.NOT_STARTED
    public var isExecuting: Boolean = false
        private set
    private val cancellationToken = CancellationToken()
    private var currentFrame: Frame = ioConfiguration.frameBuilders.aboutFrameBuilder.build(
        information.name,
        information.description,
        information.author,
    )

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
     * Display a transition frame with a specified [title] and [message].
     */
    public fun displayTransition(title: String, message: String) {
        refresh(ioConfiguration.frameBuilders.transitionFrameBuilder.build(title, message))
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
                this.information.author,
            ),
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
                commands.distinct(),
            ),
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

    internal fun execute() {
        if (isExecuting) {
            return
        }

        isExecuting = true

        refresh(
            ioConfiguration.frameBuilders.titleFrameBuilder.build(
                information.name,
                information.introduction,
            ),
        )

        var input = ""
        var reaction = Reaction(ReactionResult.ERROR, "Error.")

        do {
            var displayReactionToInput = true
            val completionCheckResult = completionCondition(this)
            val gameOverCheckResult = gameOverCondition(this)
            val converser = activeConverser

            when {
                completionCheckResult.conditionMet -> {
                    refresh(
                        ioConfiguration.frameBuilders.completionFrameBuilder.build(
                            completionCheckResult.title,
                            completionCheckResult.description,
                        ),
                    )
                    end()
                }
                gameOverCheckResult.conditionMet -> {
                    refresh(
                        ioConfiguration.frameBuilders.gameOverFrameBuilder.build(
                            gameOverCheckResult.title,
                            gameOverCheckResult.description,
                        ),
                    )
                    end()
                }
                converser != null -> {
                    refresh(
                        ioConfiguration.frameBuilders.conversationFrameBuilder.build(
                            "Conversation with ${converser.identifier.name}",
                            converser,
                            interpreter.getContextualCommandHelp(this),
                        ),
                    )
                }
            }

            if (state == GameState.ACTIVE) {
                if (!currentFrame.acceptsInput) {
                    val frame = currentFrame
                    while (!ioConfiguration.waitForAcknowledge(cancellationToken) &&
                        !cancellationToken.wasCancelled &&
                        currentFrame == frame
                    ) {
                        drawFrame(currentFrame)
                    }
                } else {
                    input = ioConfiguration.waitForCommand(cancellationToken)
                }
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
    }

    /**
     * End the [Game].
     */
    internal fun end() {
        cancellationToken.cancel()
        state = GameState.FINISHED
    }

    private fun getFallbackFrame(): Frame {
        return ioConfiguration.frameBuilders.transitionFrameBuilder.build(
            "Error",
            "Couldn't refresh frame.",
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
                    sceneMapKeyType,
                ),
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
                CustomCommandInterpreter(),
            ),
        )
    }
}
