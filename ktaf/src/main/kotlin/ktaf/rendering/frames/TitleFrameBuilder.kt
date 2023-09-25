package ktaf.rendering.frames

/**
 * Provides a standard interface for title frames.
 */
public interface TitleFrameBuilder {
    /**
     * Build a frame with a [title], [description], [width] and [height]
     */
    public fun build(title: String, description: String, width: Int, height: Int): Frame
}
