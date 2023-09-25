package ktaf.command.game

import ktaf.assets.Item
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.game.Take
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TakeTest {
    @Test
    fun `given null item when invoke then return error`() {
        // Given
        val command = Take(null)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given item not in room when invoke then return error`() {
        // Given
        val item = Item("", "")
        val command = Take(item)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given item in room when invoke then return ok`() {
        // Given
        val item = Item("", "", true)
        val command = Take(item)
        val game = GameTestHelper.getBlankGame()
        game.overworld.currentRegion?.currentRoom?.addItem(item)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
