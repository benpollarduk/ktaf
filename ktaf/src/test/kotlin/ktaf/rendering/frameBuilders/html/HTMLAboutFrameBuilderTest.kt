package ktaf.rendering.frameBuilders.html

import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.html.HTMLAboutFrameBuilder
import ktaf.rendering.frames.html.HTMLPageBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HTMLAboutFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HTMLAboutFrameBuilder(HTMLPageBuilder())

        // When
        val result = builder.build("Test", GameTestHelper.getBlankGame()).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
