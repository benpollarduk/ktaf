package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ANSITitleFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = ANSITitleFrameBuilder(ANSIGridStringBuilder(), Size(80, 50))

        // When
        val result = builder.build("Test", "Test").toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
