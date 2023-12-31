package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.rendering.frames.AboutFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.Frame

/**
 * Provides an HTML about frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlAboutFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : AboutFrameBuilder {
    override fun build(title: String, description: String, author: String): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.p(description.ensureFinishedSentence())
        htmlPageBuilder.br()

        if (author.isNotEmpty()) {
            htmlPageBuilder.h4("Created by $author.")
        } else {
            htmlPageBuilder.h4("ktaf by Ben Pollard 2023.")
        }

        return HtmlFrame(htmlPageBuilder, false)
    }
}
