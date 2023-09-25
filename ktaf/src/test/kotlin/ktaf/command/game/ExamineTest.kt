package ktaf.command.game

import ktaf.assets.Item
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.game.Examine
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExamineTest {
    @Test
    fun `given null item when invoke then return error`() {
        // Given
        val command = Examine(null)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given valid game when invoke then return ok`() {
        // Given
        val command = Examine(Item("", ""))

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
