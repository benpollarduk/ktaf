package com.github.benpollarduk.ktaf.assets

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DescriptionTest {

    @Test
    fun `given abc when calling getDescription then return abc`() {
        // Given
        val description = Description("abc")

        // When
        val result = description.getDescription()

        // Then
        assertEquals("abc", result)
    }
}
