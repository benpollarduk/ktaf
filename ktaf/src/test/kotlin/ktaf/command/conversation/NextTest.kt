package ktaf.command.conversation

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.conversation.Next
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NextTest {
    @Test
    fun `given no converser when invoke then return error`() {
        // Given
        val command = Next()

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given converser when invoke then return internal`() {
        // Given
        val command = Next()
        val game = GameTestHelper.getBlankGame()
        game.startConversation(NonPlayableCharacter("", ""))

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.INTERNAL, result.result)
    }
}
