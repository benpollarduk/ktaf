package com.github.benpollarduk.ktaf.rendering.frames

import com.github.benpollarduk.ktaf.conversations.Converser
import com.github.benpollarduk.ktaf.interpretation.CommandHelp

/**
 * Provides a standard interface for conversation frames.
 */
public fun interface ConversationFrameBuilder {
    /**
     * Build a frame with a [title], [converser] and [commands].
     */
    public fun build(title: String, converser: Converser, commands: List<CommandHelp>): Frame
}
