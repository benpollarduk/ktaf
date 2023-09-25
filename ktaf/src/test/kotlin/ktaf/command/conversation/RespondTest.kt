package ktaf.command.conversation

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.conversation.Respond
import ktaf.conversations.Response
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RespondTest {
    @Test
    fun `given no converser when invoke then return error`() {
        // Given
        val command = Respond(Response("", 1))

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given converser when invoke with invalid response then return error`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val npc = NonPlayableCharacter("", "")
        val command = Respond(Response("", 1))
        game.startConversation(npc)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given converser when invoke with valid response then return internal`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val response = Response("", 1)
        val npc = NonPlayableCharacter("", "")
        val command = Respond(response)
        game.startConversation(npc)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }
}
