package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NextTest {
    @Test
    fun `given currently on first when get index of next then return 1`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Next()

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given currently on second when get index of next then return 2`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Next()

        // When
        val result = instruction.getIndexOfNext(paragraphs[1], paragraphs)

        // Then
        Assertions.assertEquals(2, result)
    }

    @Test
    fun `given currently on third and only three when get index of next then return 2`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Next()

        // When
        val result = instruction.getIndexOfNext(paragraphs[2], paragraphs)

        // Then
        Assertions.assertEquals(2, result)
    }
}
