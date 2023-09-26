package ktaf.rendering.frames

/**
 * Provides a standard interface for title frames.
 */
public interface TitleFrameBuilder {
    /**
     * Build a frame for a with a [title] and [introduction].
     */
    public fun build(title: String, introduction: String): Frame
}
