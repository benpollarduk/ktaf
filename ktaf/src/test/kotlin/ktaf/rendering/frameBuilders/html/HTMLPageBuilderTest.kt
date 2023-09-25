package ktaf.rendering.frameBuilders.html

import ktaf.rendering.frames.html.HTMLPageBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HTMLPageBuilderTest {
    @Test
    fun `given test when h1 then correctly formatted h1 returned`() {
        // Given
        val builder = HTMLPageBuilder()

        // When
        builder.h1("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h1>Test</h1>", result)
    }

    @Test
    fun `given test when h2 then correctly formatted h2 returned`() {
        // Given
        val builder = HTMLPageBuilder()

        // When
        builder.h2("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h2>Test</h2>", result)
    }

    @Test
    fun `given test when h3 then correctly formatted h3 returned`() {
        // Given
        val builder = HTMLPageBuilder()

        // When
        builder.h3("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<h3>Test</h3>", result)
    }

    @Test
    fun `given test when p then correctly formatted p returned`() {
        // Given
        val builder = HTMLPageBuilder()

        // When
        builder.p("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<p>Test</p>", result)
    }

    @Test
    fun `given test when u then correctly formatted p returned`() {
        // Given
        val builder = HTMLPageBuilder()

        // When
        builder.u("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<u>Test</u>", result)
    }

    @Test
    fun `given test when i then correctly formatted p returned`() {
        // Given
        val builder = HTMLPageBuilder()

        // When
        builder.i("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<i>Test</i>", result)
    }

    @Test
    fun `given test when b then correctly formatted p returned`() {
        // Given
        val builder = HTMLPageBuilder()

        // When
        builder.b("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<b>Test</b>", result)
    }

    @Test
    fun `given test when append then correctly formatted p returned`() {
        // Given
        val builder = HTMLPageBuilder()

        // When
        builder.append("Test")
        val result = builder.toString()

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given br then correctly formatted br returned`() {
        // Given
        val builder = HTMLPageBuilder()
        builder.br()
        val result = builder.toString()

        // Then
        Assertions.assertEquals("<br>", result)
    }
}
