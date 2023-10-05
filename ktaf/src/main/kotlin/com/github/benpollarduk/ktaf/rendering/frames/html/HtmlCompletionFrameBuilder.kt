package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.rendering.frames.CompletionFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.Frame

/**
 * Provides an HTML completion frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlCompletionFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : CompletionFrameBuilder {
    override fun build(title: String, reason: String): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.p(reason.ensureFinishedSentence())

        return HtmlFrame(htmlPageBuilder, false)
    }
}
