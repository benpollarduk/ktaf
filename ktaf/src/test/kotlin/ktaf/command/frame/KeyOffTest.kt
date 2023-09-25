package ktaf.command.frame

import ktaf.assets.interaction.ReactionResult
import ktaf.commands.frame.KeyOff
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KeyOffTest {
    @Test
    fun `given valid game when invoke then return ok`() {
        // Given
        val command = KeyOff()

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
