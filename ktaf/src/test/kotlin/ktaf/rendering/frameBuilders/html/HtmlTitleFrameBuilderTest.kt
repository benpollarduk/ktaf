package ktaf.rendering.frameBuilders.html

import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.html.HtmlPageBuilder
import ktaf.rendering.frames.html.HtmlTitleFrameBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlTitleFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HtmlTitleFrameBuilder(HtmlPageBuilder())

        // When
        val result = builder.build(GameTestHelper.getBlankGame()).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
