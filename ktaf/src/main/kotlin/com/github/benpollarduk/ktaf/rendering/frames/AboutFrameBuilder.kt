package com.github.benpollarduk.ktaf.rendering.frames

/**
 * Provides a standard interface for about frames.
 */
public fun interface AboutFrameBuilder {
    /**
     * Build a frame with a [title], [description] and [author].
     */
    public fun build(title: String, description: String, author: String): Frame
}
