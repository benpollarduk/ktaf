package ktaf.commands.frame

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game
import ktaf.rendering.KeyType

/**
 * Provides a command to turn on displaying the key.
 */
internal class KeyOn : Command {
    override fun invoke(game: Game): Reaction {
        game.sceneMapKeyType = KeyType.DYNAMIC
        return Reaction(ReactionResult.OK, "Key has been turned on.")
    }
}
