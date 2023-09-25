package ktaf.io

import ktaf.rendering.FramePosition

/**
 * Provides an interface for adjusting the input.
 */
public interface AdjustInput {
    /**
     * Adjust the cursor. [allowInput] specifies if input is allowed and [cursorPosition] specifies the cursor position
     * relative to the top left of the current frame.
     */
    public operator fun invoke(allowInput: Boolean, cursorPosition: FramePosition)
}
