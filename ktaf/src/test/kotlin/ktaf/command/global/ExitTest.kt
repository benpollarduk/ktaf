package ktaf.command.global

import ktaf.assets.interaction.ReactionResult
import ktaf.commands.global.Exit
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExitTest {
    @Test
    fun `given valid game when invoke then return ok`() {
        // Given
        val command = Exit()

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
