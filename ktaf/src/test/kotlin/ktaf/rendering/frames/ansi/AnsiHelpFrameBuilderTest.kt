package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import ktaf.interpretation.CommandHelp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiHelpFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiHelpFrameBuilder(AnsiGridStringBuilder(), Size(80, 50))

        // When
        val result = builder.build(
            "Test",
            "This is a test frame",
            listOf(CommandHelp("TestCommand", "Test Command Description."))
        ).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}