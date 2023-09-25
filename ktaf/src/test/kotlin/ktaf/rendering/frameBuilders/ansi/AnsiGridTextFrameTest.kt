package ktaf.rendering.frameBuilders.ansi

import ktaf.assets.Size
import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.rendering.frames.ansi.AnsiColor
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import ktaf.rendering.frames.ansi.AnsiGridTextFrame
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiGridTextFrameTest {
    @Test
    fun `given grid string builder when rendering test text then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val builder = AnsiGridStringBuilder().also {
                it.resize(Size(75, 50))
            }
            builder.drawBoundary(AnsiColor.BLUE)
            builder.drawWrapped("TEST", 5, 5, 50, AnsiColor.GREEN)
            val frame = AnsiGridTextFrame(builder, 10, 10, AnsiColor.BLACK).also {
                it.acceptsInput = false
            }

            // When
            frame.render(AnsiConsoleConfiguration.displayTextOutput)
        }
    }
}
