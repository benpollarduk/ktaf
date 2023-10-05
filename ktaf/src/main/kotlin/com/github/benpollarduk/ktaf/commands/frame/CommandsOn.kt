package com.github.benpollarduk.ktaf.commands.frame

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a command to turn on displaying the commands list.
 */
internal class CommandsOn : Command {
    override fun invoke(game: Game): Reaction {
        game.displayCommandListInSceneFrames = true
        return Reaction(ReactionResult.OK, "Commands have been turned on.")
    }
}
