package ktaf.rendering.frameBuilders.html

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.conversations.Conversation
import ktaf.conversations.Paragraph
import ktaf.conversations.Response
import ktaf.interpretation.ConversationCommandInterpreter
import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.html.HtmlConversationFrameBuilder
import ktaf.rendering.frames.html.HtmlPageBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlConversationFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HtmlConversationFrameBuilder(HtmlPageBuilder())
        val game = GameTestHelper.getBlankGame()
        NonPlayableCharacter("Test Character", "A Test Character").also { character ->
            val paragraph1 = Paragraph("First I said this.")
            val paragraph2 = Paragraph("Then I said a load more.")
            val paragraph3 = Paragraph("A test paragraph, this is something that is said.").also {
                it.responses = listOf(
                    Response("Response a.", 1),
                    Response("Response b.", 1),
                    Response("Response c.", 1)
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
            commands,
            game
        ).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
