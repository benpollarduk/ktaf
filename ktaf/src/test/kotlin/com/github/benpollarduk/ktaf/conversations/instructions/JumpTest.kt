package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class JumpTest {
    @Test
    fun `given 1 two paragraphs on first when get index of next then return 1`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Jump(1)

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given minus 1 two paragraphs on second when get index of next then return 0`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Jump(-1)

        // When
        val result = instruction.getIndexOfNext(paragraphs[1], paragraphs)

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given minus 1 two paragraphs on first when get index of next then return 0`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Jump(-1)

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given 8 two paragraphs on first when get index of next then return 1`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("")
        )
        val instruction = Jump(8)

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(1, result)
    }
}
