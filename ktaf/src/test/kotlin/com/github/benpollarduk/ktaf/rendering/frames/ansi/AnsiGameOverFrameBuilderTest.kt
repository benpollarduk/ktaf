package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.Size
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiGameOverFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiGameOverFrameBuilder(AnsiGridStringBuilder(), Size(80, 50))

        // When
        val result = builder.build("Test", "This is a test frame").toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
