package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.commands.game.Unactionable
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides an [Interpreter] for input. This [Interpreter] acts as a master containing a collection of child
 * interpreters.
 */
public class InputInterpreter(private val interpreters: List<Interpreter> = emptyList()) : Interpreter {
    override val supportedCommands: List<CommandHelp>
        get() {
            val list = interpreters
                .map { it.supportedCommands }
                .flatten()
                .toTypedArray()

            return list.toList()
        }

    override fun interpret(input: String, game: Game): InterpretationResult {
        interpreters.forEach {
            val result = it.interpret(input, game)

            if (result.interpretedSuccessfully) {
                return result
            }
        }

        return InterpretationResult(false, Unactionable("Could not interpret $input."))
    }

    override fun getContextualCommandHelp(game: Game): List<CommandHelp> {
        val list = interpreters
            .map { it.getContextualCommandHelp(game) }
            .flatten()
            .toTypedArray()

        return list.toList()
    }
}
