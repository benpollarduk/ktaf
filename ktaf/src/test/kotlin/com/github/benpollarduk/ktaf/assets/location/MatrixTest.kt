package com.github.benpollarduk.ktaf.assets.location

import com.github.benpollarduk.ktaf.assets.locations.Matrix
import com.github.benpollarduk.ktaf.assets.locations.Room
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MatrixTest {
    @Test
    fun `given no rooms when calling width then return 0`() {
        // Given
        val matrix = Matrix(Array(0) { Array(0) { Array(0) { Room.empty } } })

        // When
        val result = matrix.width

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given no rooms when calling height then return 0`() {
        // Given
        val matrix = Matrix(Array(0) { Array(0) { Array(0) { Room.empty } } })

        // When
        val result = matrix.height

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given no rooms when calling depth then return 0`() {
        // Given
        val matrix = Matrix(Array(0) { Array(0) { Array(0) { Room.empty } } })

        // When
        val result = matrix.depth

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given one room width when calling width then return 1`() {
        // Given
        val rooms = Array(1) { Array(2) { Array(3) { Room.empty } } }
        rooms[0][1][2] = Room("", "")
        rooms[0][0][0] = Room("", "")
        rooms[0][1][0] = Room("", "")
        rooms[0][1][1] = Room("", "")

        val matrix = Matrix(rooms)

        // When
        val result = matrix.width

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given two room height when calling height then return 2`() {
        // Given
        val rooms = Array(1) { Array(2) { Array(3) { Room.empty } } }
        rooms[0][1][2] = Room("", "")
        rooms[0][0][0] = Room("", "")
        rooms[0][1][0] = Room("", "")
        rooms[0][1][1] = Room("", "")

        val matrix = Matrix(rooms)

        // When
        val result = matrix.height

        // Then
        Assertions.assertEquals(2, result)
    }

    @Test
    fun `given three room depth when calling depth then return 3`() {
        // Given
        val rooms = Array(1) { Array(2) { Array(3) { Room.empty } } }
        rooms[0][1][2] = Room("", "")
        rooms[0][0][0] = Room("", "")
        rooms[0][1][0] = Room("", "")
        rooms[0][1][1] = Room("", "")

        val matrix = Matrix(rooms)

        // When
        val result = matrix.depth

        // Then
        Assertions.assertEquals(3, result)
    }

    @Test
    fun `given 1x2x3 matrix with 0 rooms when calling toRoomPositions then return array with 0 elements`() {
        // Given
        val rooms = Array(1) { Array(2) { Array(3) { Room.empty } } }
        val matrix = Matrix(rooms)

        // When
        val result = matrix.toRoomPositions()

        // Then
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `given 1x2x3 matrix with 4 rooms when calling toRoomPositions then return array with 4 elements`() {
        // Given
        val rooms = Array(1) { Array(2) { Array(3) { Room.empty } } }
        rooms[0][1][2] = Room("", "")
        rooms[0][0][0] = Room("", "")
        rooms[0][1][0] = Room("", "")
        rooms[0][1][1] = Room("", "")

        val matrix = Matrix(rooms)

        // When
        val result = matrix.toRoomPositions()

        // Then
        Assertions.assertEquals(4, result.size)
    }

    @Test
    fun `given empty matrix when calling isEmpty then return true`() {
        // Given
        val rooms = Array(0) { Array(0) { Array(0) { Room.empty } } }
        val matrix = Matrix(rooms)

        // When
        val result = matrix.isEmpty

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a non-empty matrix when calling isEmpty then return false`() {
        // Given
        val rooms = Array(1) { Array(2) { Array(3) { Room.empty } } }
        rooms[0][1][2] = Room("", "")

        val matrix = Matrix(rooms)

        // When
        val result = matrix.isEmpty

        // Then
        Assertions.assertFalse(result)
    }
}
