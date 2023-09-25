package ktaf.commands

import ktaf.assets.interaction.Reaction
import ktaf.logic.Game

/**
 * Provides a contract for in game commands.
 */
public interface Command {
    /**
     * Invoke the [Command] on the [game] to obtain a [Reaction].
     */
    public fun invoke(game: Game): Reaction
}
