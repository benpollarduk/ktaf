package ktaf.rendering.frames.html

import ktaf.conversations.Converser
import ktaf.conversations.Participant
import ktaf.interpretation.CommandHelp
import ktaf.rendering.frames.ConversationFrameBuilder
import ktaf.rendering.frames.Frame

/**
 * Provides an HTML conversation frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlConversationFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder
) : ConversationFrameBuilder {
    override fun build(title: String, converser: Converser, commands: List<CommandHelp>): Frame {
        val log = converser.conversation.log

        if (title.isNotEmpty()) {
            htmlPageBuilder.h1(title)
            htmlPageBuilder.br()
            htmlPageBuilder.br()
        }

        if (log.any()) {
            log.forEach {
                val converserName = converser.identifier.name

                when (it.participant) {
                    Participant.PLAYER -> {
                        htmlPageBuilder.b("You: ")
                        htmlPageBuilder.append(it.line)
                    }
                    Participant.OTHER -> {
                        htmlPageBuilder.b("$converserName: ")
                        htmlPageBuilder.append(it.line)
                    }
                }

                htmlPageBuilder.br()
            }
        }

        if (commands.any()) {
            htmlPageBuilder.br()
            htmlPageBuilder.br()

            commands.forEach {
                htmlPageBuilder.b(it.command)
                htmlPageBuilder.append(" - ${it.description}")
                htmlPageBuilder.br()
            }
        }

        return HtmlFrame(htmlPageBuilder).also {
            it.acceptsInput = true
        }
    }
}
