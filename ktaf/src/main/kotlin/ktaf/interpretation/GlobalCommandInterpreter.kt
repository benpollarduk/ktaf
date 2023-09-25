package ktaf.interpretation

import ktaf.commands.global.About
import ktaf.commands.global.Exit
import ktaf.commands.global.Help
import ktaf.commands.global.Map
import ktaf.commands.global.New
import ktaf.extensions.insensitiveEquals
import ktaf.logic.Game

/**
 * Provides an [Interpreter] for global commands.
 */
public class GlobalCommandInterpreter : Interpreter {
    override val supportedCommands: List<CommandHelp>
        get() = GlobalCommandInterpreter.supportedCommands

    override fun interpret(input: String, game: Game): InterpretationResult {
        return when {
            input.insensitiveEquals(about) -> InterpretationResult(true, About())
            input.insensitiveEquals(exit) -> InterpretationResult(true, Exit())
            input.insensitiveEquals(help) -> InterpretationResult(true, Help())
            input.insensitiveEquals(map) -> InterpretationResult(true, Map())
            input.insensitiveEquals(new) -> InterpretationResult(true, New())
            else -> InterpretationResult.fail
        }
    }

    override fun getContextualCommandHelp(game: Game): List<CommandHelp> {
        return emptyList()
    }

    public companion object {
        /**
         * Get the string for the [About] command.
         */
        public const val about: String = "About"

        /**
         * Get the string for the [Exit] command.
         */
        public const val exit: String = "Exit"

        /**
         * Get the string for the [Help] command.
         */
        public const val help: String = "Help"

        /**
         * Get the string for the [Map] command.
         */
        public const val map: String = "Map"

        /**
         * Get the string for the [New] command.
         */
        public const val new: String = "New"

        /**
         * Get the supported commands.
         */
        public val supportedCommands: List<CommandHelp> = listOf(
            CommandHelp(about, "View information about the games creator"),
            CommandHelp(exit, "Exit the game"),
            CommandHelp(help, "Display help"),
            CommandHelp(map, "View a map of the current region"),
            CommandHelp(new, "Start a new game")
        )
    }
}
