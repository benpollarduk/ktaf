package com.github.benpollarduk.ktaf.io

import com.github.benpollarduk.ktaf.rendering.frames.FrameBuilderCollection

/**
 * Provides a configuration for handling input and output for a [Game].
 */
public interface IOConfiguration {
    /**
     * Specifies how frames should be rendered.
     */
    public val renderFrame: RenderFrame

    /**
     * Specifies how acknowledgments should be waited for.
     */
    public val waitForAcknowledge: WaitForAcknowledge

    /**
     * Specifies how commands should be waited for.
     */
    public val waitForCommand: WaitForCommand

    /**
     * Specifies the frame builders used to render the [Game].
     */
    public val frameBuilders: FrameBuilderCollection
}
