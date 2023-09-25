package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.logic.Game
import ktaf.rendering.frames.CompletionFrameBuilder
import ktaf.rendering.frames.Frame

/**
 * Provides an HTML completion frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlCompletionFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : CompletionFrameBuilder {
    override fun build(title: String, reason: String, game: Game): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.br()
        htmlPageBuilder.p(reason.ensureFinishedSentence())

        return HtmlFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
