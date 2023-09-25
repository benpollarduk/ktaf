package ktaf.assets.location

import ktaf.assets.locations.Direction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DirectionTest {
    @Test
    fun `given north when inverse then return south`() {
        // Given
        val value = Direction.NORTH

        // When
        val result = value.inverse()

        // Then
        Assertions.assertEquals(Direction.SOUTH, result)
    }

    @Test
    fun `given south when inverse then return north`() {
        // Given
        val value = Direction.SOUTH

        // When
        val result = value.inverse()

        // Then
        Assertions.assertEquals(Direction.NORTH, result)
    }

    @Test
    fun `given east when inverse then return west`() {
        // Given
        val value = Direction.EAST

        // When
        val result = value.inverse()

        // Then
        Assertions.assertEquals(Direction.WEST, result)
    }

    @Test
    fun `given west when inverse then return east`() {
        // Given
        val value = Direction.WEST

        // When
        val result = value.inverse()

        // Then
        Assertions.assertEquals(Direction.EAST, result)
    }

    @Test
    fun `given up when inverse then return down`() {
        // Given
        val value = Direction.UP

        // When
        val result = value.inverse()

        // Then
        Assertions.assertEquals(Direction.DOWN, result)
    }

    @Test
    fun `given down when inverse then return up`() {
        // Given
        val value = Direction.DOWN

        // When
        val result = value.inverse()

        // Then
        Assertions.assertEquals(Direction.UP, result)
    }
}
