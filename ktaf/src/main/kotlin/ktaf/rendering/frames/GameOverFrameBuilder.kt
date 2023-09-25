package ktaf.rendering.frames

/**
 * Provides a standard interface for game over frames.
 */
public interface GameOverFrameBuilder {
    /**
     * Build a frame with a [title], [reason], [width] and [height]
     */
    public fun build(title: String, reason: String, width: Int, height: Int): Frame
}
