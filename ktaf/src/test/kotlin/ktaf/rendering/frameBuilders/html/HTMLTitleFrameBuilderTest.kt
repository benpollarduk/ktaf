package ktaf.rendering.frameBuilders.html

import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.html.HTMLPageBuilder
import ktaf.rendering.frames.html.HTMLTitleFrameBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HTMLTitleFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HTMLTitleFrameBuilder(HTMLPageBuilder())

        // When
        val result = builder.build(GameTestHelper.getBlankGame()).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
