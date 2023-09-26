package ktaf.rendering.frames

import ktaf.conversations.Converser
import ktaf.interpretation.CommandHelp

/**
 * Provides a standard interface for conversation frames.
 */
public interface ConversationFrameBuilder {
    /**
     * Build a frame with a [title], [converser] and [commands].
     */
    public fun build(title: String, converser: Converser, commands: List<CommandHelp>): Frame
}
