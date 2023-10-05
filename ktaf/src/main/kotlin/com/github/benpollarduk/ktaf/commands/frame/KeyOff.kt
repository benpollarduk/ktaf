package com.github.benpollarduk.ktaf.commands.frame

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game
import com.github.benpollarduk.ktaf.rendering.KeyType

/**
 * Provides a command to turn off displaying the key.
 */
internal class KeyOff : Command {
    override fun invoke(game: Game): Reaction {
        game.sceneMapKeyType = KeyType.NONE
        return Reaction(ReactionResult.OK, "Key has been turned off.")
    }
}
