package com.github.benpollarduk.ktaf.commands.game

import com.github.benpollarduk.ktaf.assets.Examinable
import com.github.benpollarduk.ktaf.assets.ExaminationScene
import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a command to examine a specified [examinable].
 */
internal class Examine(private val examinable: Examinable?) : Command {
    override fun invoke(game: Game): Reaction {
        if (examinable == null) {
            return Reaction(ReactionResult.ERROR, "You must specify an examinable.")
        }

        return Reaction(
            ReactionResult.OK,
            examinable.examine(
                ExaminationScene(
                    game.player,
                    game.overworld.currentRegion?.currentRoom ?: Room("", "")
                )
            ).description
        )
    }
}
