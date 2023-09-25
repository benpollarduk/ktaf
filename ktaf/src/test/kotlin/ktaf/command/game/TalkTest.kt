package ktaf.command.game

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.game.Talk
import ktaf.conversations.Conversation
import ktaf.conversations.Paragraph
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TalkTest {
    @Test
    fun `given null converser when invoke then return error`() {
        // Given
        val command = Talk(null)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given dead converser when invoke then return error`() {
        // Given
        val converser = NonPlayableCharacter("", "").also {
            it.kill()
        }
        val command = Talk(converser)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given live converser in room when invoke then return internal`() {
        // Given
        val converser = NonPlayableCharacter("", "").also {
            it.conversation = Conversation(listOf(Paragraph("")))
        }
        val command = Talk(converser)
        val game = GameTestHelper.getBlankGame()
        game.overworld.currentRegion?.currentRoom?.addCharacter(converser)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.INTERNAL, result.result)
    }
}
