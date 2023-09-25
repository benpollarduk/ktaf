package ktaf.rendering.frameBuilders.ansi

import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import ktaf.rendering.frames.ansi.AnsiTitleFrameBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiTitleFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiTitleFrameBuilder(AnsiGridStringBuilder())

        // When
        val result = builder.build(GameTestHelper.getBlankGame()).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
