package com.github.benpollarduk.ktaf.rendering.frames.html

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlPageBuilderTest {
    @Test
    fun `given test when h1 then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.h1("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h1>Test</h1>", result)
    }

    @Test
    fun `given test when h2 then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.h2("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h2>Test</h2>", result)
    }

    @Test
    fun `given test when h3 then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.h3("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h3>Test</h3>", result)
    }

    @Test
    fun `given test when h4 then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.h4("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h4>Test</h4>", result)
    }

    @Test
    fun `given test when p then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.p("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<p>Test</p>", result)
    }

    @Test
    fun `given test when u then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.u("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<u>Test</u>", result)
    }

    @Test
    fun `given test when i then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.i("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<i>Test</i>", result)
    }

    @Test
    fun `given test when b then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.b("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<b>Test</b>", result)
    }

    @Test
    fun `given test when append then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.append("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given test when pre then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.pre("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<pre>Test</pre>", result)
    }

    @Test
    fun `given br then correctly formatted html returned`() {
        // Given
        val builder = HtmlPageBuilder()
        builder.br()
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<br>", result)
    }
}
