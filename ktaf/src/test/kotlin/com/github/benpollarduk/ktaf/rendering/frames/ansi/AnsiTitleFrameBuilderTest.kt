package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.Size
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiTitleFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiTitleFrameBuilder(AnsiGridStringBuilder(), Size(80, 50))

        // When
        val result = builder.build("Test", "Test").toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
