package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiGridFrameBuilderTest {
    @Test
    fun `given blank when draw horizontal border then border is drawn`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        builder.drawHorizontalDivider(0, AnsiColor.BLACK)
        val left = builder.getCharacter(1, 0)
        val right = builder.getCharacter(8, 0)

        // Then
        Assertions.assertEquals(builder.horizontalDivider, left)
        Assertions.assertEquals(builder.horizontalDivider, right)
    }

    @Test
    fun `given blank when draw underline then underline is drawn`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        builder.drawUnderline(0, 0, 1, AnsiColor.BLACK)
        val underline = builder.getCharacter(0, 0)

        // Then
        Assertions.assertEquals('-', underline)
    }

    @Test
    fun `given blank when draw boundary then boundary is drawn`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        builder.drawBoundary(AnsiColor.BLACK)
        val topLeft = builder.getCharacter(0, 0)
        val topRight = builder.getCharacter(9, 0)
        val bottomLeft = builder.getCharacter(9, 0)
        val bottomRight = builder.getCharacter(9, 9)

        // Then
        Assertions.assertEquals(builder.leftBoundary, topLeft)
        Assertions.assertEquals(builder.rightBoundary, topRight)
        Assertions.assertEquals(builder.leftBoundary, bottomLeft)
        Assertions.assertEquals(builder.rightBoundary, bottomRight)
    }

    @Test
    fun `given blank when draw wrapped with A then drawn character is A`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        builder.drawWrapped("A", 0, 0, 10, AnsiColor.BLACK)
        val result = builder.getCharacter(0, 0)

        // Then
        Assertions.assertEquals('A', result)
    }

    @Test
    fun `given blank when draw wrapped with AA then end position x is 1`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        val result = builder.drawWrapped("AA", 0, 0, 10, AnsiColor.BLACK)

        // Then
        Assertions.assertEquals(1, result.x)
    }

    @Test
    fun `given blank when draw wrapped with AA then end position y is 0`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        val result = builder.drawWrapped("AA", 0, 0, 10, AnsiColor.BLACK)

        // Then
        Assertions.assertEquals(0, result.y)
    }

    @Test
    fun `given a string that exceeds the width when draw wrapped then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val builder = AnsiGridStringBuilder().also {
                it.resize(Size(80, 50))
            }
            val longString = "There is a Beaver. Person is in Test Room. To the north is the South Room, " +
                "east is the East Room, south is the North Room, west is the West Room, above is the " +
                "Top Room, below is the Bottom Room."

            // When
            builder.drawWrapped(longString, 0, 0, 80, AnsiColor.BLACK)
        }
    }

    @Test
    fun `given blank when draw centralised wrapped with A then drawn character is A`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        builder.drawCentralisedWrapped("A", 0, 10, AnsiColor.BLACK)
        val result = builder.getCharacter(5, 0)

        // Then
        Assertions.assertEquals('A', result)
    }

    @Test
    fun `given blank when centralised draw wrapped with AA then end position x is 5`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        val result = builder.drawCentralisedWrapped("AA", 0, 10, AnsiColor.BLACK)

        // Then
        Assertions.assertEquals(5, result.x)
    }

    @Test
    fun `given blank when centralised draw wrapped with AA then end position y is 0`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        val result = builder.drawCentralisedWrapped("AA", 0, 10, AnsiColor.BLACK)

        // Then
        Assertions.assertEquals(0, result.y)
    }

    @Test
    fun `given display size of 10x10 and a line of -and the do- with when get number of lines then return 1`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        val result = builder.getNumberOfLines("and the do", 0, 10)

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given display size of 10x10 and a line of -and the dog- with when get number of lines then return 2`() {
        // Given
        val builder = AnsiGridStringBuilder().also {
            it.resize(Size(10, 10))
        }

        // When
        val result = builder.getNumberOfLines("and the dog", 0, 10)

        // Then
        Assertions.assertEquals(2, result)
    }
}
