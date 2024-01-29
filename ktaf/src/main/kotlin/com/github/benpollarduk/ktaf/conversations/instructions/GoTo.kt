package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph

/**
 * An end of paragraph instruction that shifts paragraphs based on an absolute [index].
 */
public class GoTo(public val index: Int) : EndOfParagraphInstruction {
    override fun getIndexOfNext(current: Paragraph, paragraphs: List<Paragraph>): Int {
        return if (index < 0) {
            0
        } else if (index >= paragraphs.size) {
            paragraphs.size - 1
        } else {
            index
        }
    }
}
