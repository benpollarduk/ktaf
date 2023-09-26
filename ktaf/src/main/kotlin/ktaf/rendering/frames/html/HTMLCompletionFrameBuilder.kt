package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.rendering.frames.CompletionFrameBuilder
import ktaf.rendering.frames.Frame

/**
 * Provides an HTML completion frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HTMLCompletionFrameBuilder(
    private val htmlPageBuilder: HTMLPageBuilder
) : CompletionFrameBuilder {
    override fun build(title: String, reason: String): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.br()
        htmlPageBuilder.p(reason.ensureFinishedSentence())

        return HTMLFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
