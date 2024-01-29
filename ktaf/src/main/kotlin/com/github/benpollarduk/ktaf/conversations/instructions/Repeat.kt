package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph

/**
 * An end of paragraph instruction that repeats.
 */
public class Repeat : EndOfParagraphInstruction {
    override fun getIndexOfNext(current: Paragraph, paragraphs: List<Paragraph>): Int {
        val index = paragraphs.indexOf(current)
        return if (index < 0) {
            0
        } else {
            index
        }
    }
}
