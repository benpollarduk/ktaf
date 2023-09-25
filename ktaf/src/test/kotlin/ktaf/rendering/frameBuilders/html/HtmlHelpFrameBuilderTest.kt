package ktaf.rendering.frameBuilders.html

import ktaf.interpretation.CommandHelp
import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.html.HtmlHelpFrameBuilder
import ktaf.rendering.frames.html.HtmlPageBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlHelpFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HtmlHelpFrameBuilder(HtmlPageBuilder())

        // When
        val result = builder.build(
            "Test",
            "This is a test frame",
            listOf(CommandHelp("TestCommand", "Test Command Description.")),
            GameTestHelper.getBlankGame()
        ).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
