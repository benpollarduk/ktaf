package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ToNameTest {
    @Test
    fun `given name of first when get index of next then return 0`() {
        // Given
        val paragraphs = listOf(
            Paragraph("", name = "Test"),
            Paragraph("")
        )
        val instruction = ToName("Test")

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given name of second when get index of next then return 1`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("", name = "Test"),
            Paragraph("")
        )
        val instruction = ToName("Test")

        // When
        val result = instruction.getIndexOfNext(paragraphs[1], paragraphs)

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given invalid name when get index of next then return 0`() {
        // Given
        val paragraphs = listOf(
            Paragraph(""),
            Paragraph("", name = "Test"),
            Paragraph("")
        )
        val instruction = ToName("1234")

        // When
        val result = instruction.getIndexOfNext(paragraphs[1], paragraphs)

        // Then
        Assertions.assertEquals(0, result)
    }
}
