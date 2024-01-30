package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph

/**
 * An end of paragraph instruction that shifts paragraphs based on a callback.
 */
public class ByCallback(public val callback: () -> EndOfParagraphInstruction) : EndOfParagraphInstruction {
    override fun getIndexOfNext(current: Paragraph, paragraphs: List<Paragraph>): Int {
        return callback().getIndexOfNext(current, paragraphs)
    }
}
