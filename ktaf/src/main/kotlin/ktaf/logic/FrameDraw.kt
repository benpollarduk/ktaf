package ktaf.logic

import ktaf.rendering.frames.Frame

/**
 * Provides a lambda signature for drawing a [Game].
 */
public typealias FrameDraw = (Frame) -> Unit
