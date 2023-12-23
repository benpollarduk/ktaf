package com.github.benpollarduk.ktaf.rendering.frames

/**
 * Provides a standard interface for title frames.
 */
public fun interface TitleFrameBuilder {
    /**
     * Build a frame for a with a [title] and [introduction].
     */
    public fun build(title: String, introduction: String): Frame
}
