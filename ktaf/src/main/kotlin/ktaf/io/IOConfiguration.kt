package ktaf.io

import ktaf.rendering.frames.FrameBuilderCollection

/**
 * Provides a configuration for handling input and output for a [Game].
 */
public interface IOConfiguration {
    /**
     * Specifies how text should be output.
     */
    public val displayTextOutput: DisplayTextOutput

    /**
     * Specifies how acknowledgments should be waited for.
     */
    public val waitForAcknowledge: WaitForAcknowledge

    /**
     * Specifies how commands should be waited for.
     */
    public val waitForCommand: WaitForCommand

    /**
     * Specifies how the output should be cleared.
     */
    public val clearOutput: ClearOutput

    /**
     * Specifies how the input should be adjusted.
     */
    public val adjustInput: AdjustInput

    /**
     * Specifies the frame builders used to render the [Game].
     */
    public val frameBuilders: FrameBuilderCollection
}
