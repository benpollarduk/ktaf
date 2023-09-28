package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.TitleFrameBuilder

/**
 * Provides an HTML title frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlTitleFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : TitleFrameBuilder {
    override fun build(title: String, introduction: String): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.p(introduction.ensureFinishedSentence())

        return HtmlFrame(htmlPageBuilder, false)
    }
}
