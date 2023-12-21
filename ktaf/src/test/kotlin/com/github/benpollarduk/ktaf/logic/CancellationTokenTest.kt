package com.github.benpollarduk.ktaf.logic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CancellationTokenTest {
    @Test
    fun `given token when cancelled then was cancelled is true`() {
        // Given
        val token = CancellationToken()

        // When
        token.cancel()

        // Then
        Assertions.assertTrue(token.wasCancelled)
    }
}
