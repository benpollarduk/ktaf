package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.logic.Game
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.TitleFrameBuilder

/**
 * Provides an HTML title frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlTitleFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : TitleFrameBuilder {
    override fun build(game: Game): Frame {
        htmlPageBuilder.h1(game.name)
        htmlPageBuilder.br()
        htmlPageBuilder.p(game.description.ensureFinishedSentence())

        return HtmlFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
