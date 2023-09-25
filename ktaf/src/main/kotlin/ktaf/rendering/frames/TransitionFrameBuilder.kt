package ktaf.rendering.frames

/**
 * Provides a standard interface for transition frames.
 */
public interface TransitionFrameBuilder {
    /**
     * Build a frame with a [title], [message], [width] and [height]
     */
    public fun build(title: String, message: String, width: Int, height: Int): Frame
}
