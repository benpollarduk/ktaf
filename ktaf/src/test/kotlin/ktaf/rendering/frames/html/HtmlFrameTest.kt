package ktaf.rendering.frames.html

import ktaf.io.configurations.AnsiConsoleConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlFrameTest {
    @Test
    fun `given blank builder in body mode when render then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val frame = HtmlFrame(HtmlPageBuilder())

            // When
            frame.render(AnsiConsoleConfiguration.renderFrame)
        }
    }

    @Test
    fun `given blank builder in document mode when render then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val frame = HtmlFrame(HtmlPageBuilder(HtmlElementType.Document()))
            print(frame.toString())

            // When
            frame.render(AnsiConsoleConfiguration.renderFrame)
        }
    }
}
