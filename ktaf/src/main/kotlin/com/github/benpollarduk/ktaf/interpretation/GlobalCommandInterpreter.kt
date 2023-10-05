package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.commands.global.About
import com.github.benpollarduk.ktaf.commands.global.Exit
import com.github.benpollarduk.ktaf.commands.global.Help
import com.github.benpollarduk.ktaf.commands.global.Map
import com.github.benpollarduk.ktaf.commands.global.New
import com.github.benpollarduk.ktaf.extensions.insensitiveEquals
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides an [Interpreter] for global commands.
 */
public class GlobalCommandInterpreter : Interpreter {
    override val supportedCommands: List<CommandHelp>
        get() = GlobalCommandInterpreter.supportedCommands

    override fun interpret(input: String, game: Game): InterpretationResult {
        return when {
            input.insensitiveEquals(ABOUT) -> InterpretationResult(true, About())
            input.insensitiveEquals(EXIT) -> InterpretationResult(true, Exit())
            input.insensitiveEquals(HELP) -> InterpretationResult(true, Help())
            input.insensitiveEquals(MAP) -> InterpretationResult(true, Map())
            input.insensitiveEquals(NEW) -> InterpretationResult(true, New())
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
        public const val ABOUT: String = "About"

        /**
         * Get the string for the [Exit] command.
         */
        public const val EXIT: String = "Exit"

        /**
         * Get the string for the [Help] command.
         */
        public const val HELP: String = "Help"

        /**
         * Get the string for the [Map] command.
         */
        public const val MAP: String = "Map"

        /**
         * Get the string for the [New] command.
         */
        public const val NEW: String = "New"

        /**
         * Get the supported commands.
         */
        public val supportedCommands: List<CommandHelp> = listOf(
            CommandHelp(ABOUT, "View information about the games creator"),
            CommandHelp(EXIT, "Exit the game"),
            CommandHelp(HELP, "Display help"),
            CommandHelp(MAP, "View a map of the current region"),
            CommandHelp(NEW, "Start a new game")
        )
    }
}
