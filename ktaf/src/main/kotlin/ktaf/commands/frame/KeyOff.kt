package ktaf.commands.frame

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game
import ktaf.rendering.KeyType

/**
 * Provides a command to turn off displaying the key.
 */
internal class KeyOff : Command {
    override fun invoke(game: Game): Reaction {
        game.sceneMapKeyType = KeyType.NONE
        return Reaction(ReactionResult.OK, "Key has been turned off.")
    }
}
