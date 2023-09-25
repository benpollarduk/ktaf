package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.logic.Game
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.GameOverFrameBuilder

/**
 * Provides an HTML game over frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlGameOverFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : GameOverFrameBuilder {
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
