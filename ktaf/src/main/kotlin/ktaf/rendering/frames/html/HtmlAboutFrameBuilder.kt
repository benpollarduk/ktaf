package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.rendering.frames.AboutFrameBuilder
import ktaf.rendering.frames.Frame

/**
 * Provides an HTML about frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlAboutFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : AboutFrameBuilder {
    override fun build(title: String, description: String, author: String): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.br()
        htmlPageBuilder.p(description.ensureFinishedSentence())
        htmlPageBuilder.br()
        htmlPageBuilder.br()

        if (author.isNotEmpty()) {
            htmlPageBuilder.h2("Created by $author.")
        } else {
            htmlPageBuilder.h2("ktaf by Ben Pollard 2023.")
        }

        return HtmlFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
