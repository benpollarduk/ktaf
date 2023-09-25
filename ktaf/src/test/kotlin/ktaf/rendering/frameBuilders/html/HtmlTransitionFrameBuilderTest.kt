package ktaf.rendering.frameBuilders.html

import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.html.HtmlPageBuilder
import ktaf.rendering.frames.html.HtmlTransitionFrameBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlTransitionFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HtmlTransitionFrameBuilder(HtmlPageBuilder())

        // When
        val result = builder.build("Test", "Test Transition", GameTestHelper.getBlankGame()).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
