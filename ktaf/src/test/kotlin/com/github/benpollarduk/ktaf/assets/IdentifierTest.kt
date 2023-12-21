package com.github.benpollarduk.ktaf.assets

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class IdentifierTest {
    @Test
    fun `given a name of abc when calling equals with def then return false`() {
        // Given
        val identifier = Identifier("abc")

        // When
        val result = identifier.equals("def")

        // Then
        assertFalse(result)
    }

    @Test
    fun `given a name of abc when calling equals with abc then return true`() {
        // Given
        val identifier = Identifier("abc")

        // When
        val result = identifier.equals("abc")

        // Then
        assertTrue(result)
    }

    @Test
    fun `given a name of ABC when calling equals with abc then return true`() {
        // Given
        val identifier = Identifier("ABC")

        // When
        val result = identifier.equals("abc")

        // Then
        assertTrue(result)
    }

    @Test
    fun `given a name of abc when calling equals with an Identifier with a name of def then return false`() {
        // Given
        val identifier = Identifier("abc")

        // When
        val result = identifier == Identifier("def")

        // Then
        assertFalse(result)
    }

    @Test
    fun `given a name of abc when calling equals with an Identifier with a name of abc then return true`() {
        // Given
        val identifier = Identifier("abc")

        // When
        val result = identifier == Identifier("abc")

        // Then
        assertTrue(result)
    }

    @Test
    fun `given a name of ABC when calling equals with an Identifier with a name of abc then return true`() {
        // Given
        val identifier = Identifier("ABC")

        // When
        val result = identifier == Identifier("abc")

        // Then
        assertTrue(result)
    }

    @Test
    fun `given 2 identifiers when calling hash code then different has codes returned`() {
        // Given
        val identifier1 = Identifier("ABC")
        val identifier2 = Identifier("XYZ")

        // When
        val result1 = identifier1.hashCode()
        val result2 = identifier2.hashCode()

        // Then
        assertTrue(result1 != result2)
    }
}
