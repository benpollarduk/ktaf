package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph

/**
 * An end of paragraph instruction that shifts paragraphs based on a name.
 */
public class ToName(public val name: String) : EndOfParagraphInstruction {
    override fun getIndexOfNext(current: Paragraph, paragraphs: List<Paragraph>): Int {
        val target = paragraphs.firstOrNull {
            it.name.equals(name, true)
        }

        return if (target != null) {
            paragraphs.indexOf(target)
        } else {
            0
        }
    }
}
