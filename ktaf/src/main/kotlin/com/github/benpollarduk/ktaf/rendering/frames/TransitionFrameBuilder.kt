package com.github.benpollarduk.ktaf.rendering.frames

/**
 * Provides a standard interface for transition frames.
 */
public fun interface TransitionFrameBuilder {
    /**
     * Build a frame with a [title] and [message].
     */
    public fun build(title: String, message: String): Frame
}
