package ktaf.rendering.frames

import ktaf.logic.Game

/**
 * Provides a standard interface for completion frames.
 */
public interface CompletionFrameBuilder {
    /**
     * Build a frame with a [title], [reason] and [game].
     */
    public fun build(title: String, reason: String, game: Game): Frame
}
