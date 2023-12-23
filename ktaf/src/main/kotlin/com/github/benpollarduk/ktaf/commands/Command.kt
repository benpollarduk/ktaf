package com.github.benpollarduk.ktaf.commands

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a contract for in game commands.
 */
public fun interface Command {
    /**
     * Invoke the [Command] on the [game] to obtain a [Reaction].
     */
    public fun invoke(game: Game): Reaction
}
