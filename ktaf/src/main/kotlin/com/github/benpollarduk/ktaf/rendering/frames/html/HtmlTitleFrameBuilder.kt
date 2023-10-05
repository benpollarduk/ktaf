package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.rendering.frames.Frame
import com.github.benpollarduk.ktaf.rendering.frames.TitleFrameBuilder

/**
 * Provides an HTML title frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlTitleFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : TitleFrameBuilder {
    override fun build(title: String, introduction: String): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()
        htmlPageBuilder.p(introduction.ensureFinishedSentence())

        return HtmlFrame(htmlPageBuilder, false)
    }
}
