package com.github.benpollarduk.ktaf.commands.frame

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game
import com.github.benpollarduk.ktaf.rendering.KeyType

/**
 * Provides a command to turn on displaying the key.
 */
internal class KeyOn : Command {
    override fun invoke(game: Game): Reaction {
        game.sceneMapKeyType = KeyType.DYNAMIC
        return Reaction(ReactionResult.OK, "Key has been turned on.")
    }
}
