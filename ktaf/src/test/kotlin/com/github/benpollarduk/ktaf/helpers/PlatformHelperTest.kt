package com.github.benpollarduk.ktaf.helpers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class PlatformHelperTest {
    @Test
    fun `given windows when calling newline then return carriage return line feed`() {
        // Given
        val value = "windows"

        // When
        val result = newline(value)

        // Then
        Assertions.assertEquals("\r\n", result)
    }

    @Test
    fun `given linux when calling newline then return line feed`() {
        // Given
        val value = "linux"

        // When
        val result = newline(value)

        // Then
        Assertions.assertEquals("\n", result)
    }

    @Test
    fun `given no specified os when calling newline then no exception is thrown`() {
        // Then
        assertDoesNotThrow {
            // Given
            val value = "linux"

            // When
            newline(value)
        }
    }
}
