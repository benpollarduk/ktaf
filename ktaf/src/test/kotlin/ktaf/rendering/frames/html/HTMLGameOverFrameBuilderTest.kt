package ktaf.rendering.frames.html

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HTMLGameOverFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HTMLGameOverFrameBuilder(HTMLPageBuilder())

        // When
        val result = builder.build("Test", "Test Reason").toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
