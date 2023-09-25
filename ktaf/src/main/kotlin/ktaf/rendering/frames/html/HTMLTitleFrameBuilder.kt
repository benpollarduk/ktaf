package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.logic.Game
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.TitleFrameBuilder

/**
 * Provides an HTML title frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HTMLTitleFrameBuilder(
    private val htmlPageBuilder: HTMLPageBuilder
) : TitleFrameBuilder {
    override fun build(game: Game): Frame {
        htmlPageBuilder.h1(game.name)
        htmlPageBuilder.br()
        htmlPageBuilder.p(game.description.ensureFinishedSentence())

        return HTMLFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
