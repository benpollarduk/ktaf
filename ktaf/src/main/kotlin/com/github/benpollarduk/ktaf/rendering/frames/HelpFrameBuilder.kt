package com.github.benpollarduk.ktaf.rendering.frames

import com.github.benpollarduk.ktaf.interpretation.CommandHelp

/**
 * Provides a standard interface for help frames.
 */
public fun interface HelpFrameBuilder {
    /**
     * Build a frame with a [title], [description] and [commands].
     */
    public fun build(title: String, description: String, commands: List<CommandHelp>): Frame
}
