package ktaf.rendering.frames.html

import ktaf.conversations.Participant
import ktaf.interpretation.CommandHelp
import ktaf.logic.Game
import ktaf.rendering.frames.ConversationFrameBuilder
import ktaf.rendering.frames.Frame

/**
 * Provides an HTML conversation frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlConversationFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : ConversationFrameBuilder {
    override fun build(title: String, commands: List<CommandHelp>, game: Game): Frame {
        val converser = game.activeConverser
        val log = converser?.conversation?.log ?: emptyList()

        if (title.isNotEmpty()) {
            htmlPageBuilder.h1(title)
            htmlPageBuilder.br()
            htmlPageBuilder.br()
        }

        if (log.any()) {
            log.forEach {
                val converserName = converser?.identifier?.name

                when (it.participant) {
                    Participant.PLAYER -> {
                        htmlPageBuilder.p("You: ${it.line}")
                    }
                    Participant.OTHER -> {
                        htmlPageBuilder.p("$converserName: ${it.line}")
                    }
                }
            }
        }

        if (commands.any()) {
            htmlPageBuilder.br()
            htmlPageBuilder.br()

            commands.forEach {
                htmlPageBuilder.p("${it.command} - ${it.description}")
            }
        }

        return HtmlFrame(htmlPageBuilder).also {
            it.acceptsInput = true
        }
    }
}
