package ktaf.rendering.frames

import ktaf.interpretation.CommandHelp
import ktaf.logic.Game

/**
 * Provides a standard interface for conversation frames.
 */
public interface ConversationFrameBuilder {
    /**
     * Build a frame with a [title], [commands] and [game].
     */
    public fun build(title: String, commands: List<CommandHelp>, game: Game): Frame
}
