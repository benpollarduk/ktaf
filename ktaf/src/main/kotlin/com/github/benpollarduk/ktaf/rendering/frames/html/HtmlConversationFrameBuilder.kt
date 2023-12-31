package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.conversations.Converser
import com.github.benpollarduk.ktaf.conversations.Participant
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import com.github.benpollarduk.ktaf.rendering.frames.ConversationFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.Frame

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

            commands.forEach {
                htmlPageBuilder.br()
                htmlPageBuilder.b(it.command)
                htmlPageBuilder.append(" - ${it.description}")
            }
        }

        return HtmlFrame(htmlPageBuilder, true)
    }
}
