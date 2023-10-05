package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.commands.frame.CommandsOff
import com.github.benpollarduk.ktaf.commands.frame.CommandsOn
import com.github.benpollarduk.ktaf.commands.frame.KeyOff
import com.github.benpollarduk.ktaf.commands.frame.KeyOn
import com.github.benpollarduk.ktaf.extensions.insensitiveEquals
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides an [Interpreter] for frame commands.
 */
public class FrameCommandInterpreter : Interpreter {
    override val supportedCommands: List<CommandHelp>
        get() = FrameCommandInterpreter.supportedCommands

    override fun interpret(input: String, game: Game): InterpretationResult {
        if (input.insensitiveEquals(COMMANDS_OFF)) {
            return InterpretationResult(true, CommandsOff())
        }

        if (input.insensitiveEquals(COMMANDS_ON)) {
            return InterpretationResult(true, CommandsOn())
        }

        if (input.insensitiveEquals(KEY_ON)) {
            return InterpretationResult(true, KeyOn())
        }

        if (input.insensitiveEquals(KEY_OFF)) {
            return InterpretationResult(true, KeyOff())
        }

        return InterpretationResult.fail
    }

    override fun getContextualCommandHelp(game: Game): List<CommandHelp> {
        return emptyList()
    }

    public companion object {
        /**
         * Get the string for the [CommandsOff] command.
         */
        public const val COMMANDS_OFF: String = "CommandsOff"

        /**
         * Get the string for the [CommandsOn] command.
         */
        public const val COMMANDS_ON: String = "CommandsOn"

        /**
         * Get the string for the [KeyOff] command.
         */
        public const val KEY_OFF: String = "KeyOff"

        /**
         * Get the string for the [KeyOn] command.
         */
        public const val KEY_ON: String = "KeyOn"

        /**
         * Get the supported commands.
         */
        public val supportedCommands: List<CommandHelp> = listOf(
            CommandHelp("$COMMANDS_ON / $COMMANDS_ON", "Turn commands on/off"),
            CommandHelp("$KEY_ON / $KEY_OFF", "Turn the key on/off")
        )
    }
}
