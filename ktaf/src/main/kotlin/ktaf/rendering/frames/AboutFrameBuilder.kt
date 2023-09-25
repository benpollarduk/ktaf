package ktaf.rendering.frames

import ktaf.logic.Game

/**
 * Provides a standard interface for about frames.
 */
public interface AboutFrameBuilder {
    /**
     * Build a frame with a [title] and [game].
     */
    public fun build(title: String, game: Game): Frame
}
