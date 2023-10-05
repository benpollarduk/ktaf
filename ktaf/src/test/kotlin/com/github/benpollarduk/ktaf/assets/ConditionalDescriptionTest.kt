package com.github.benpollarduk.ktaf.assets

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConditionalDescriptionTest {

    @Test
    fun `given a true description of abc when calling getDescription and condition returns true then return abc`() {
        // Given
        val description = com.github.benpollarduk.ktaf.assets.ConditionalDescription("abc", "def") { true }

        // When
        val result = description.getDescription()

        // Then
        assertEquals("abc", result)
    }

    @Test
    fun `given a false description of def when calling getDescription and condition returns false then return def`() {
        // Given
        val description = com.github.benpollarduk.ktaf.assets.ConditionalDescription("abc", "def") { false }

        // When
        val result = description.getDescription()

        // Then
        assertEquals("def", result)
    }
}
