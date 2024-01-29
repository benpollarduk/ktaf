package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph

/**
 * An end of paragraph instruction that shifts paragraphs to the end.
 */
public class Last : EndOfParagraphInstruction {
    override fun getIndexOfNext(current: Paragraph, paragraphs: List<Paragraph>): Int {
        return paragraphs.size - 1
    }
}
