package com.github.benpollarduk.ktaf.io

import com.github.benpollarduk.ktaf.rendering.FramePosition

/**
 * Provides an interface for rendering a frame.
 */
public interface RenderFrame {
    /**
     * Display the specified [frame] on the output. [allowInput] specifies if input is allowed and [cursorPosition]
     * specifies the cursor position relative to the top left of the current frame.
     */
    public operator fun invoke(
        frame: String,
        allowInput: Boolean,
        cursorPosition: FramePosition
    )
}
