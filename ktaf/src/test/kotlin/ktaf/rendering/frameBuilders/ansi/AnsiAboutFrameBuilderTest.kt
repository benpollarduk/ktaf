package ktaf.rendering.frameBuilders.ansi

import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.ansi.AnsiAboutFrameBuilder
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiAboutFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiAboutFrameBuilder(AnsiGridStringBuilder())

        // When
        val result = builder.build("Test", GameTestHelper.getBlankGame(), 80, 50).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
