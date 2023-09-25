package ktaf.rendering.frames

import ktaf.assets.locations.Room
import ktaf.assets.locations.ViewPoint
import ktaf.interpretation.CommandHelp
import ktaf.logic.Game
import ktaf.rendering.KeyType

/**
 * Provides a standard interface for scene frames.
 */
public interface SceneFrameBuilder {
    /**
     * Build a frame with a [room], [viewPoint], [game], [message], [contextualCommands] and [keyType].
     */
    public fun build(
        room: Room,
        viewPoint: ViewPoint,
        game: Game,
        message: String,
        contextualCommands: List<CommandHelp>,
        keyType: KeyType
    ): Frame
}
