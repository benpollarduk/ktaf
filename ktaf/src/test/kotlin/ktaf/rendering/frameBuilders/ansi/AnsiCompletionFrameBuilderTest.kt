package ktaf.rendering.frameBuilders.ansi

import ktaf.rendering.frames.ansi.AnsiCompletionFrameBuilder
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiCompletionFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiCompletionFrameBuilder(AnsiGridStringBuilder())

        // When
        val result = builder.build("Test", "This is a test frame", 80, 50).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
