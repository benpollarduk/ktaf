package com.github.benpollarduk.ktaf.conversations.instructions

import com.github.benpollarduk.ktaf.conversations.Paragraph
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ByCallbackTest {
    @Test
    fun `given callback when get index of next then return 1`() {
        // Given
        val paragraphs = listOf(
            Paragraph("", name = "Test"),
            Paragraph("")
        )
        val instruction = ByCallback {
            Last()
        }

        // When
        val result = instruction.getIndexOfNext(paragraphs[0], paragraphs)

        // Then
        Assertions.assertEquals(1, result)
    }
}
