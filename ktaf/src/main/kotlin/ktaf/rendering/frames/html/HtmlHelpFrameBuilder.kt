package ktaf.rendering.frames.html

import ktaf.extensions.ensureFinishedSentence
import ktaf.interpretation.CommandHelp
import ktaf.logic.Game
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.HelpFrameBuilder

/**
 * Provides an HTML help frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlHelpFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : HelpFrameBuilder {
    override fun build(title: String, description: String, commands: List<CommandHelp>, game: Game): Frame {
        htmlPageBuilder.h1(game.name)
        htmlPageBuilder.br()
        htmlPageBuilder.br()

        if (description.isNotEmpty()) {
            htmlPageBuilder.p(description.ensureFinishedSentence())
            htmlPageBuilder.br()
            htmlPageBuilder.br()
        }

        commands.forEach {
            htmlPageBuilder.p("${it.command} - ${it.description}")
        }

        return HtmlFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
