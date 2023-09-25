package ktaf.command.global

import ktaf.assets.interaction.ReactionResult
import ktaf.commands.global.About
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AboutTest {
    @Test
    fun `given valid game when invoke then return internal`() {
        // Given
        val command = About()

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.INTERNAL, result.result)
    }
}
