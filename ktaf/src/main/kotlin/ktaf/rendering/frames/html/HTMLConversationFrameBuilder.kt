package ktaf.rendering.frames.html

import ktaf.conversations.Participant
import ktaf.interpretation.CommandHelp
import ktaf.logic.Game
import ktaf.rendering.frames.ConversationFrameBuilder
import ktaf.rendering.frames.Frame

/**
 * Provides an HTML conversation frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HTMLConversationFrameBuilder(
    private val htmlPageBuilder: HTMLPageBuilder
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

        return HTMLFrame(htmlPageBuilder).also {
            it.acceptsInput = true
        }
    }
}
