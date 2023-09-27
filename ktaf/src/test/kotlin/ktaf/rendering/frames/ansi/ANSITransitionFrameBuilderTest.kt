package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ANSITransitionFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = ANSITransitionFrameBuilder(ANSIGridStringBuilder(), Size(80, 50))

        // When
        val result = builder.build("Test", "This is a test frame").toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
