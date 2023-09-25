package ktaf.command.game

import ktaf.assets.Item
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.game.UseOn
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UseOnTest {
    @Test
    fun `given null item when invoke then return error`() {
        // Given
        val command = UseOn(null, null)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given null target when invoke then return error`() {
        // Given
        val command = UseOn(Item("", ""), null)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given valid item and target when invoke then return ok`() {
        // Given
        val item = Item("", "")
        val game = GameTestHelper.getBlankGame()
        val command = UseOn(item, game.player)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
