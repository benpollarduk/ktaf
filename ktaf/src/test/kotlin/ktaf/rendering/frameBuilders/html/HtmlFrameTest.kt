package ktaf.rendering.frameBuilders.html

import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.rendering.frames.html.HtmlFrame
import ktaf.rendering.frames.html.HtmlPageBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlFrameTest {
    @Test
    fun `given black builder when render then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val frame = HtmlFrame(HtmlPageBuilder())

            // When
            frame.render(AnsiConsoleConfiguration.displayTextOutput)
        }
    }
}
