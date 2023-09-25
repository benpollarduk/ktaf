package ktaf.command.game

import ktaf.assets.interaction.ReactionResult
import ktaf.commands.game.Unactionable
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UnactionableTest {
    @Test
    fun `given valid game when invoke then return error`() {
        // Given
        val command = Unactionable("")

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }
}
