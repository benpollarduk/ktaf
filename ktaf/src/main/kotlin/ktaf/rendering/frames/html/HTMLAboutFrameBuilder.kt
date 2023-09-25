package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.logic.Game
import ktaf.rendering.frames.AboutFrameBuilder
import ktaf.rendering.frames.Frame

/**
 * Provides an HTML about frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HTMLAboutFrameBuilder(
    private val htmlPageBuilder: HTMLPageBuilder
) : AboutFrameBuilder {
    override fun build(title: String, game: Game): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.br()
        htmlPageBuilder.p(game.description.ensureFinishedSentence())
        htmlPageBuilder.br()
        htmlPageBuilder.br()

        if (game.author.isNotEmpty()) {
            htmlPageBuilder.h2("Created by ${game.author}.")
        } else {
            htmlPageBuilder.h2("BP.AdventureFramework by Ben Pollard 2011 - 2023.")
        }

        return HTMLFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
