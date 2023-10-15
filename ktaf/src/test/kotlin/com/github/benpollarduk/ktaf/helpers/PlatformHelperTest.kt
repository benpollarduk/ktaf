package com.github.benpollarduk.ktaf.helpers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PlatformHelperTest {
    @Test
    fun `given newline then return line feed`() {
        // Given
        val result = newline()

        // Then
        Assertions.assertEquals("\n", result)
    }
}
