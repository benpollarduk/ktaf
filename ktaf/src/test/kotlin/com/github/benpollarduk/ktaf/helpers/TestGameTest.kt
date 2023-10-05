package com.github.benpollarduk.ktaf.helpers

import com.github.benpollarduk.ktaf.TestGame
import com.github.benpollarduk.ktaf.io.configurations.AnsiConsoleConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestGameTest {
    @Test
    fun `given get simple debug then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            TestGame.instantiate(AnsiConsoleConfiguration)
        }
    }
}
