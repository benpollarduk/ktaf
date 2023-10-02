package ktaf.helpers

import ktaf.TestGame
import ktaf.io.configurations.AnsiConsoleConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestGameTest {
    @Test
    fun `given get simple debug then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            TestGame.get(AnsiConsoleConfiguration)
        }
    }
}
