package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import ktaf.io.configurations.AnsiConsoleConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ANSIGridTextFrameTest {
    @Test
    fun `given grid string builder when rendering test text then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val builder = ANSIGridStringBuilder().also {
                it.resize(Size(75, 50))
            }
            builder.drawBoundary(ANSIColor.BLUE)
            builder.drawWrapped("TEST", 5, 5, 50, ANSIColor.GREEN)
            val frame = ANSIGridTextFrame(builder, 10, 10, ANSIColor.BLACK).also {
                it.acceptsInput = false
            }

            // When
            frame.render(AnsiConsoleConfiguration.displayTextOutput)
        }
    }
}
