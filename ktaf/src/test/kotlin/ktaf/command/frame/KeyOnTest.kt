package ktaf.command.frame

import ktaf.assets.interaction.ReactionResult
import ktaf.commands.frame.KeyOn
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KeyOnTest {
    @Test
    fun `given valid game when invoke then return ok`() {
        // Given
        val command = KeyOn()

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
