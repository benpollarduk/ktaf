package com.github.benpollarduk.ktaf.assets.location

import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.RoomPosition
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RoomPositionTest {
    @Test
    fun `given a room at 0,0,0 exit when checking for 1,0,0 then return isLocked returns false`() {
        // Given
        val position = RoomPosition(Room("", ""), 0, 0, 0)

        // When
        val result = position.isAtPosition(1, 0, 0)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a room at 0,0,0 exit when checking for 0,1,0 then return isLocked returns false`() {
        // Given
        val position = RoomPosition(Room("", ""), 0, 0, 0)

        // When
        val result = position.isAtPosition(0, 1, 0)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a room at 0,0,0 exit when checking for 0,0,1 then return isLocked returns false`() {
        // Given
        val position = RoomPosition(Room("", ""), 0, 0, 0)

        // When
        val result = position.isAtPosition(0, 0, 1)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a room at 0,0,0 exit when checking for 0,0,0 then return isLocked returns true`() {
        // Given
        val position = RoomPosition(Room("", ""), 0, 0, 0)

        // When
        val result = position.isAtPosition(0, 0, 0)

        // Then
        Assertions.assertTrue(result)
    }
}
