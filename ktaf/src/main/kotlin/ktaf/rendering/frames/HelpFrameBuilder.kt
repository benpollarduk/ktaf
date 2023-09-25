package ktaf.rendering.frames

import ktaf.interpretation.CommandHelp
import ktaf.logic.Game

/**
 * Provides a standard interface for help frames.
 */
public interface HelpFrameBuilder {
    /**
     * Build a frame with a [title], [description], [commands] and [game].
     */
    public fun build(title: String, description: String, commands: List<CommandHelp>, game: Game): Frame
}
