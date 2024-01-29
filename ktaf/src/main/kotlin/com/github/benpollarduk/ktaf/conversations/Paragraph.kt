package com.github.benpollarduk.ktaf.conversations

import com.github.benpollarduk.ktaf.conversations.instructions.EndOfParagraphInstruction
import com.github.benpollarduk.ktaf.conversations.instructions.Next

/**
 * A paragraph within a [Conversation]. Must contain a [line], but can also have an optional [action] that is invoked
 * as a response to this paragraph being triggered. The [instruction] is applied to direct the conversation after this
 * paragraph.
 */
public class Paragraph(
    public val line: String,
    public val instruction: EndOfParagraphInstruction = Next(),
    public val name: String = "",
    public val action: ConversationAction = { }
) {
    /**
     * The available responses to the [line].
     */
    public var responses: List<Response> = emptyList()

    /**
     * Determines if there are [responses] available for this [Paragraph].
     */
    public val canRespond: Boolean
        get() = responses.any()
}
