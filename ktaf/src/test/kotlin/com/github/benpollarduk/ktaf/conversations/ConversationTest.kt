package com.github.benpollarduk.ktaf.conversations

import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class ConversationTest {
    @Test
    fun `given a conversation with one paragraph when constructed then return current paragraph is first paragraph`() {
        // Given
        val conversation = Conversation(listOf(Paragraph("Test")))
        val game = GameTestHelper.getBlankGame()

        // When
        conversation.next(game)
        val result = conversation.currentParagraph?.line ?: ""

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given a conversation with no paragraph when next called then no exception is thrown`() {
        // Then
        assertDoesNotThrow {
            // Given
            val conversation = Conversation(emptyList())
            val game = GameTestHelper.getBlankGame()

            // When
            conversation.next(game)
        }
    }

    @Test
    fun `given a conversation with no paragraphs when next called then currentParagraph is null`() {
        // Given
        val conversation = Conversation(emptyList())
        val game = GameTestHelper.getBlankGame()

        // When
        conversation.next(game)
        val result = conversation.currentParagraph

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given a conversation with one paragraph when next called then currentParagraph is not null`() {
        // Given
        val conversation = Conversation(listOf(Paragraph("")))
        val game = GameTestHelper.getBlankGame()

        // When
        conversation.next(game)
        val result = conversation.currentParagraph

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given a conversation with one paragraph when next called then result is internal`() {
        // Given
        val conversation = Conversation(listOf(Paragraph("")))
        val game = GameTestHelper.getBlankGame()

        // When
        val result = conversation.next(game).result

        // Then
        Assertions.assertEquals(ReactionResult.INTERNAL, result)
    }

    @Test
    fun `given a conversation with one paragraph when next called twice then currentParagraph is unchanged`() {
        // Given
        val conversation = Conversation(listOf(Paragraph("")))
        val game = GameTestHelper.getBlankGame()

        // When
        conversation.next(game)
        val startParagraph = conversation.currentParagraph
        conversation.next(game)
        val result = conversation.currentParagraph

        // Then
        Assertions.assertEquals(startParagraph, result)
    }

    @Test
    fun `given a conversation with two paragraphs when next called twice then currentParagraph is second paragraph`() {
        // Given
        val conversation = Conversation(listOf(Paragraph(""), Paragraph("2")))
        val game = GameTestHelper.getBlankGame()

        // When
        conversation.next(game)
        conversation.next(game)
        val result = conversation.currentParagraph?.line

        // Then
        Assertions.assertEquals("2", result)
    }

    @Test
    fun `given a conversation with a current paragraph with no responses when respond called then reaction is error`() {
        // Given
        val conversation = Conversation(listOf(Paragraph("")))
        val game = GameTestHelper.getBlankGame()

        // When
        conversation.next(game)
        val result = conversation.respond(Response(""), game).result

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result)
    }

    @Test
    fun `given a conversation when respond with a valid response called then reaction is internal`() {
        // Given
        val response = Response("")
        val paragraph = Paragraph("").also {
            it.responses = listOf(response)
        }
        val conversation = Conversation(listOf(paragraph))
        val game = GameTestHelper.getBlankGame()

        // When
        conversation.next(game)
        val result = conversation.respond(response, game).result

        // Then
        Assertions.assertEquals(ReactionResult.INTERNAL, result)
    }
}
