package ktaf.rendering.frames

import ktaf.interpretation.CommandHelp

/**
 * Provides a standard interface for help frames.
 */
public interface HelpFrameBuilder {
    /**
     * Build a frame with a [title], [description] and [commands].
     */
    public fun build(title: String, description: String, commands: List<CommandHelp>): Frame
}
