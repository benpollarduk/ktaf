package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph

/**
 * An end of paragraph instruction that shifts paragraphs based on a delta.
 */
public class Jump(public val delta: Int) : EndOfParagraphInstruction {
    override fun getIndexOfNext(current: Paragraph, paragraphs: List<Paragraph>): Int {
        val index = paragraphs.indexOf(current)
        val offset = index + delta
        return if (offset < 0) {
            0
        } else if (offset >= paragraphs.size) {
            paragraphs.size - 1
        } else {
            offset
        }
    }
}
