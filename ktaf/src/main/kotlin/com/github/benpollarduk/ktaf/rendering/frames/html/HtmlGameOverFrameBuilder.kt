package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.rendering.frames.Frame
import com.github.benpollarduk.ktaf.rendering.frames.GameOverFrameBuilder

/**
 * Provides an HTML game over frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlGameOverFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : GameOverFrameBuilder {
    override fun build(title: String, reason: String): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.p(reason.ensureFinishedSentence())

        return HtmlFrame(htmlPageBuilder, false)
    }
}
