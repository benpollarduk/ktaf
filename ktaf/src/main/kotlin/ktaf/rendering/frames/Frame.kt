package ktaf.rendering.frames

import ktaf.io.DisplayTextOutput

/**
 * Provides an interface displaying a command based interface.
 */
public interface Frame {
    /**
     * The cursors left position.
     */
    public var cursorLeft: Int

    /**
     * The cursors top position.
     */
    public var cursorTop: Int

    /**
     * Specifies if this frame accepts input.
     */
    public var acceptsInput: Boolean

    /**
     * Render this frame using a specified [displayTextOutput] lambda to handle output.
     */
    public fun render(displayTextOutput: DisplayTextOutput)
}
