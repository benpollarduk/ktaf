package com.github.benpollarduk.ktaf.rendering.frames

import com.github.benpollarduk.ktaf.io.RenderFrame

/**
 * Provides an interface displaying a command based interface.
 */
public interface Frame {
    /**
     * Get if this frame accepts input.
     */
    public val acceptsInput: Boolean

    /**
     * Render this frame using a specified [callback] to handle output.
     */
    public fun render(callback: RenderFrame)
}
