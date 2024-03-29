package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.conversations.Conversation
import com.github.benpollarduk.ktaf.conversations.Paragraph
import com.github.benpollarduk.ktaf.conversations.Response
import com.github.benpollarduk.ktaf.conversations.instructions.Next
import com.github.benpollarduk.ktaf.interpretation.ConversationCommandInterpreter
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiConversationFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiConversationFrameBuilder(AnsiGridStringBuilder(), Size(80, 50))
        val game = GameTestHelper.getBlankGame()
        val npc = NonPlayableCharacter("Test Character", "A Test Character").also { character ->
            val paragraph1 = Paragraph("First I said this.")
            val paragraph2 = Paragraph("Then I said a load more.")
            val paragraph3 = Paragraph("A test paragraph, this is something that is said.").also {
                it.responses = listOf(
                    Response("Response a.", Next()),
                    Response("Response b.", Next()),
                    Response("Response c.", Next())
                )
            }
            character.conversation = Conversation(listOf(paragraph1, paragraph2, paragraph3))
            game.startConversation(character)
            character.conversation.next(game)
            character.conversation.next(game)
        }

        val interpreter = ConversationCommandInterpreter()
        val commands = interpreter.getContextualCommandHelp(game)

        // When
        val result = builder.build(
            "Test",
            npc,
            commands
        ).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
