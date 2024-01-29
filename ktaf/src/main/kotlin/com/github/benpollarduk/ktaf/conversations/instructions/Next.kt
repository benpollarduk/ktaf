package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph

/**
 * An end of paragraph instruction that shifts paragraphs to the next paragraph.
 */
public class Next : EndOfParagraphInstruction {
    override fun getIndexOfNext(current: Paragraph, paragraphs: List<Paragraph>): Int {
        val index = paragraphs.indexOf(current)
        return if (index == -1) {
            0
        } else if (index < paragraphs.size - 1) {
            index + 1
        } else {
            paragraphs.size - 1
        }
    }
}
