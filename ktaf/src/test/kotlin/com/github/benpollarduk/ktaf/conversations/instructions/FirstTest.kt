package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FirstTest {
    @Test
    fun `given two paragraphs when get index of next then return 0`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("")
        )
        val instruction = First()

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given three paragraphs when get index of next then return 0`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph(""),
            Paragraph("")
        )
        val instruction = First()

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(0, result)
    }
}
