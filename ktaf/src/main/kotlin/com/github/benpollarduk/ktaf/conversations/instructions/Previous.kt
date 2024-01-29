package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph

/**
 * An end of paragraph instruction that shifts paragraphs to the previous paragraph.
 */
public class Previous : EndOfParagraphInstruction {
    override fun getIndexOfNext(current: Paragraph, paragraphs: List<Paragraph>): Int {
        val index = paragraphs.indexOf(current)
        return if (index > 0) {
            index - 1
        } else {
            0
        }
    }
}
