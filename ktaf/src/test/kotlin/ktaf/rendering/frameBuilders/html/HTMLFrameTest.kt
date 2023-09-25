package ktaf.rendering.frameBuilders.html

import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.rendering.frames.html.HTMLElementType
import ktaf.rendering.frames.html.HTMLFrame
import ktaf.rendering.frames.html.HTMLPageBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HTMLFrameTest {
    @Test
    fun `given blank builder in body mode when render then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val frame = HTMLFrame(HTMLPageBuilder())

            // When
            frame.render(AnsiConsoleConfiguration.displayTextOutput)
        }
    }

    @Test
    fun `given blank builder in document mode when render then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val frame = HTMLFrame(HTMLPageBuilder(HTMLElementType.Document()))
            print(frame.toString())

            // When
            frame.render(AnsiConsoleConfiguration.displayTextOutput)
        }
    }
}
