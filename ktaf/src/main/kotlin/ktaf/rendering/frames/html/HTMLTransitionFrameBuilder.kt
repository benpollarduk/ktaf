package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.TransitionFrameBuilder

/**
 * Provides an HTML transition frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HTMLTransitionFrameBuilder(
    private val htmlPageBuilder: HTMLPageBuilder
) : TransitionFrameBuilder {
    override fun build(title: String, message: String): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.br()
        htmlPageBuilder.p(message.ensureFinishedSentence())

        return HTMLFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
