package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.io.configurations.AnsiConsoleConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.StringBuilder

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

    @Test
    fun `given grid string builder when rendering color test text then some text`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(75, 50))
        }
        builder.drawBoundary(AnsiColor.BLUE)
        builder.drawWrapped("TEST", 5, 5, 50, AnsiColor.GREEN)
        val frame = AnsiGridTextFrame(builder, 10, 10, false, AnsiColor.BLACK)
        val stringBuilder = StringBuilder()

        // When
        frame.renderColor(stringBuilder)
        val result = stringBuilder.toString()

        // Then
        Assertions.assertTrue(result.any())
    }

    @Test
    fun `given grid string builder when rendering no color test text then some text`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(75, 50))
        }
        builder.drawBoundary(AnsiColor.BLUE)
        builder.drawWrapped("TEST", 5, 5, 50, AnsiColor.GREEN)
        val frame = AnsiGridTextFrame(builder, 10, 10, false, AnsiColor.BLACK)
        val stringBuilder = StringBuilder()

        // When
        frame.renderNoColor(stringBuilder)
        val result = stringBuilder.toString()

        // Then
        Assertions.assertTrue(result.any())
    }
}
