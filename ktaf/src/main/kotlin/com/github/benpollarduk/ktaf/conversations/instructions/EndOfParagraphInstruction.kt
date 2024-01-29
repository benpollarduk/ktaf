package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph

/**
 * Provides a contract for providing instructions for ends of paragraphs.
 */
public interface EndOfParagraphInstruction {
    /**
     * Get the index of the next paragraph from a [current] paragraph and an array of [paragraphs].
     */
    public fun getIndexOfNext(current: Paragraph, paragraphs: List<Paragraph>): Int
}
