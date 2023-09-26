package ktaf.rendering.frames

/**
 * Provides a standard interface for transition frames.
 */
public interface TransitionFrameBuilder {
    /**
     * Build a frame with a [title] and [message].
     */
    public fun build(title: String, message: String): Frame
}
