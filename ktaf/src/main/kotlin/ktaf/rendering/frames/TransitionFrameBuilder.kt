package ktaf.rendering.frames

import ktaf.logic.Game

/**
 * Provides a standard interface for transition frames.
 */
public interface TransitionFrameBuilder {
    /**
     * Build a frame with a [title], [message] and [game].
     */
    public fun build(title: String, message: String, game: Game): Frame
}
