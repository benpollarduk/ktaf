package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import com.github.benpollarduk.ktaf.rendering.frames.Frame
import com.github.benpollarduk.ktaf.rendering.frames.HelpFrameBuilder

/**
 * Provides an HTML help frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlHelpFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : HelpFrameBuilder {
    override fun build(title: String, description: String, commands: List<CommandHelp>): Frame {
        htmlPageBuilder.h1(title)
        htmlPageBuilder.br()

        if (description.isNotEmpty()) {
            htmlPageBuilder.p(description.ensureFinishedSentence())
            htmlPageBuilder.br()
        }

        commands.forEach {
            htmlPageBuilder.br()
            htmlPageBuilder.b(it.command)
            htmlPageBuilder.append(" - ${it.description}")
        }

        return HtmlFrame(htmlPageBuilder, false)
    }
}
