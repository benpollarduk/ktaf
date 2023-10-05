package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides an interface for text input interpretation.
 */
public interface Interpreter {
    /**
     * Get all of the commands supported by this interpreter.
     */
    public val supportedCommands: List<CommandHelp>

    /**
     * Interpret the specified [input] for a [game].
     */
    public fun interpret(input: String, game: Game): InterpretationResult

    /**
     * Get the contextual commands for the [game] based on its current state.
     */
    public fun getContextualCommandHelp(game: Game): List<CommandHelp>
}
