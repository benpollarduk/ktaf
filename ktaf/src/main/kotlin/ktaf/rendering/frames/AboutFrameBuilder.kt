package ktaf.rendering.frames

/**
 * Provides a standard interface for about frames.
 */
public interface AboutFrameBuilder {
    /**
     * Build a frame with a [title], [description] and [author].
     */
    public fun build(title: String, description: String, author: String): Frame
}
