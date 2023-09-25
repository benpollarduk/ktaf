package ktaf.rendering.frameBuilders.ansi

import ktaf.interpretation.CommandHelp
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import ktaf.rendering.frames.ansi.AnsiHelpFrameBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiHelpFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiHelpFrameBuilder(AnsiGridStringBuilder())

        // When
        val result = builder.build(
            "Test",
            "This is a test frame",
            listOf(CommandHelp("TestCommand", "Test Command Description.")),
            80,
            50
        ).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
