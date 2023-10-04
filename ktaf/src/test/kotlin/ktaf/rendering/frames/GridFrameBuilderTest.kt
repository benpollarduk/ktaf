package ktaf.rendering.frames

import ktaf.assets.Size
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GridFrameBuilderTest {
    @Test
    fun `given blank when draw wrapped with A then drawn character is A`() {
        // Given
        val builder = GridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        builder.drawWrapped("A", 0, 0, 10)
        val result = builder.getCharacter(0, 0)

        // Then
        Assertions.assertEquals('A', result)
    }

    @Test
    fun `given blank when draw wrapped with AA then end position x is 1`() {
        // Given
        val builder = GridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        val result = builder.drawWrapped("AA", 0, 0, 10)

        // Then
        Assertions.assertEquals(1, result.x)
    }

    @Test
    fun `given blank when draw wrapped with AA then end position y is 0`() {
        // Given
        val builder = GridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        val result = builder.drawWrapped("AA", 0, 0, 10)

        // Then
        Assertions.assertEquals(0, result.y)
    }

    @Test
    fun `given a string that exceeds the width when draw wrapped then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val builder = GridStringBuilder().also {
                it.resize(Size(80, 50))
            }
            val longString = "There is a Beaver. Person is in Test Room. To the north is the South Room, east " +
                "is the East Room, south is the North Room, west is the West Room, above is the Top Room, below " +
                "is the Bottom Room."

            // When
            builder.drawWrapped(longString, 0, 0, 80)
        }
    }
}
