package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.logic.Game
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.TransitionFrameBuilder

/**
 * Provides an HTML transition frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlTransitionFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : TransitionFrameBuilder {
    override fun build(title: String, message: String, game: Game): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.br()
        htmlPageBuilder.p(message.ensureFinishedSentence())

        return HtmlFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
