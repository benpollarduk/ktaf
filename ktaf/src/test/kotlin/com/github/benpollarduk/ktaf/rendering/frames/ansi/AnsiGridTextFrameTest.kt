package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.io.configurations.AnsiConsoleConfiguration
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
            val frame = AnsiGridTextFrame(builder, 10, 10, false, AnsiColor.BLACK)

            // When
            frame.render(AnsiConsoleConfiguration.renderFrame)
        }
    }
}
