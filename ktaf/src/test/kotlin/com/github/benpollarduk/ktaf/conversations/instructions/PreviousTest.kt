package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PreviousTest {
    @Test
    fun `given currently on first when get index of next then return 0`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Previous()

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given currently on second when get index of next then return 0`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Previous()

        // When
        val result = instruction.getIndexOfNext(paragraphs[1], paragraphs)

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given currently on third when get index of next then return 1`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Previous()

        // When
        val result = instruction.getIndexOfNext(paragraphs[2], paragraphs)

        // Then
        Assertions.assertEquals(1, result)
    }
}
