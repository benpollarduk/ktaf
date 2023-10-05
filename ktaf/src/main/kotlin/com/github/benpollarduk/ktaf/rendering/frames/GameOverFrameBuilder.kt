package com.github.benpollarduk.ktaf.rendering.frames

/**
 * Provides a standard interface for game over frames.
 */
public interface GameOverFrameBuilder {
    /**
     * Build a frame with a [title] and [reason].
     */
    public fun build(title: String, reason: String): Frame
}
