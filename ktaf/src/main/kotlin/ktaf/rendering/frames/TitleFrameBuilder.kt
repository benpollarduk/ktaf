package ktaf.rendering.frames

import ktaf.logic.Game

/**
 * Provides a standard interface for title frames.
 */
public interface TitleFrameBuilder {
    /**
     * Build a frame for a [game].
     */
    public fun build(game: Game): Frame
}
