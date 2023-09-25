package ktaf.rendering.frameBuilders.ansi

import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.ansi.AnsiGameOverFrameBuilder
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiGameOverFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiGameOverFrameBuilder(AnsiGridStringBuilder())

        // When
        val result = builder.build("Test", "This is a test frame", GameTestHelper.getBlankGame()).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
