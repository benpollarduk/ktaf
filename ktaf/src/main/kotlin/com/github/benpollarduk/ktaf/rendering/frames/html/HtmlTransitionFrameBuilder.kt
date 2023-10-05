package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.rendering.frames.Frame
import com.github.benpollarduk.ktaf.rendering.frames.TransitionFrameBuilder

/**
 * Provides an HTML transition frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlTransitionFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : TransitionFrameBuilder {
    override fun build(title: String, message: String): Frame {
        if (title.isNotEmpty()) {
            htmlPageBuilder.h1(title)
            htmlPageBuilder.br()
            htmlPageBuilder.br()
        }
        htmlPageBuilder.p(message.ensureFinishedSentence())

        return HtmlFrame(htmlPageBuilder, false)
    }
}
