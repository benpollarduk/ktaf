package com.github.benpollarduk.ktaf.rendering.frames

/**
 * Provides a standard interface for completion frames.
 */
public fun interface CompletionFrameBuilder {
    /**
     * Build a frame with a [title] and [reason].
     */
    public fun build(title: String, reason: String): Frame
}
