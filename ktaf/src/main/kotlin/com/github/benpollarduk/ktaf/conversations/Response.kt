package com.github.benpollarduk.ktaf.conversations

import com.github.benpollarduk.ktaf.conversations.instructions.EndOfParagraphInstruction
import com.github.benpollarduk.ktaf.conversations.instructions.Next

/**
 * A response which forms part of a [Conversation]. The [line] forms the body of the response and the [instruction] is
 * applied to direct the conversation after this paragraph.
 */
public data class Response(
    public val line: String,
    public val instruction: EndOfParagraphInstruction = Next()
)
