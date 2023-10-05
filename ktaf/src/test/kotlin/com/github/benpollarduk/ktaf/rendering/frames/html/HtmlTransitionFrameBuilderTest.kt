package com.github.benpollarduk.ktaf.rendering.frames.html

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlTransitionFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HtmlTransitionFrameBuilder(HtmlPageBuilder())

        // When
        val result = builder.build("Test", "Test Transition").toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
