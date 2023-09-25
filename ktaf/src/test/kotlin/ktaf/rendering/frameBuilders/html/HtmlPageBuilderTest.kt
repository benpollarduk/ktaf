package ktaf.rendering.frameBuilders.html

import ktaf.rendering.frames.html.HtmlPageBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlPageBuilderTest {
    @Test
    fun `given test when h1 then correctly formatted h1 returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.h1("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h1>Test</h1>", result)
    }

    @Test
    fun `given test when h2 then correctly formatted h2 returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.h2("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h2>Test</h2>", result)
    }

    @Test
    fun `given test when h3 then correctly formatted h3 returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.h3("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h3>Test</h3>", result)
    }

    @Test
    fun `given test when p with no bold or italic then correctly formatted p returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.p("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<p>Test</p>", result)
    }

    @Test
    fun `given test when p with bold then correctly formatted p returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.p("Test", true)
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<p><b>Test</b></p>", result)
    }

    @Test
    fun `given test when p with italic then correctly formatted p returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.p("Test", false, true)
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<p><i>Test</i></p>", result)
    }

    @Test
    fun `given test when p with bold and italic then correctly formatted p returned`() {
        // Given
        val builder = HtmlPageBuilder()

        // When
        builder.p("Test", true, true)
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<p><b><i>Test</i></b></p>", result)
    }

    @Test
    fun `given br then correctly formatted br returned`() {
        // Given
        val builder = HtmlPageBuilder()
        builder.br()
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<br>", result)
    }
}
