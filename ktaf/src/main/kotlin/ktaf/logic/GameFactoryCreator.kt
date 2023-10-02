package ktaf.logic

import ktaf.interpretation.CharacterCommandInterpreter
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
import ktaf.logic.factories.GameFactory
import ktaf.logic.factories.OverworldFactory
import ktaf.logic.factories.PlayableCharacterFactory

/**
 * Provides an object for creating instances of [GameFactory].
 */
public object GameFactoryCreator {
    /**
     * Get the default prefix to use for errors.
     */
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

    /**
     * Create a new instance of a [GameFactory] for producing the [Game].
     */
    public fun create(
        information: GameInformation,
        overworldFactory: OverworldFactory,
        playerFactory: PlayableCharacterFactory,
        completionCondition: EndCheck,
        gameOverCondition: EndCheck,
        errorPrefix: String = DEFAULT_ERROR_PREFIX,
        interpreter: Interpreter = defaultInterpreters,
        ioConfiguration: IOConfiguration = AnsiConsoleConfiguration
    ): GameFactory {
        return {
            val player = playerFactory()
            Game(
                information,
                player,
                overworldFactory(player),
                completionCondition,
                gameOverCondition,
                errorPrefix,
                interpreter,
                ioConfiguration
            )
        }
    }
}
