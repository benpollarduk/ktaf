package ktaf.rendering.frames.html

import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HTMLCompletionFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HTMLCompletionFrameBuilder(HTMLPageBuilder())

        // When
        val result = builder.build("Test", "Test Reason", GameTestHelper.getBlankGame()).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
