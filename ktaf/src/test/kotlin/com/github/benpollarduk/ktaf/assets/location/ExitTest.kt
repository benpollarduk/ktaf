package com.github.benpollarduk.ktaf.assets.location

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExitTest {
    @Test
    fun `given a locked exit when calling isLocked then return true`() {
        // Given
        val exit = Exit(Direction.NORTH, true)

        // When
        val result = exit.isLocked

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given an unlocked exit when calling isLocked then return false`() {
        // Given
        val exit = Exit(Direction.NORTH, false)

        // When
        val result = exit.isLocked

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a locked exit when calling unlock then isLocked returns false`() {
        // Given
        val exit = Exit(Direction.NORTH, true)

        // When
        exit.unlock()
        val result = exit.isLocked

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given an unlocked exit when calling lock then isLocked returns true`() {
        // Given
        val exit = Exit(Direction.NORTH)

        // When
        exit.lock()
        val result = exit.isLocked

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given no description when initialising then create description`() {
        // Given
        val exit = Exit(Direction.NORTH, true)

        // When
        val result = exit.description.getDescription()

        // Then
        Assertions.assertTrue(result.isNotEmpty())
    }
}
