package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LastTest {
    @Test
    fun `given two paragraphs when get index of next then return 1`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Last()

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given three paragraphs when get index of next then return 2`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Last()

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(2, result)
    }
}
