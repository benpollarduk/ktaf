package ktaf.helpers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DebugHelperTest {
    @Test
    fun `given get simple debug then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            DebugHelper.getSimpleGameCreator()
        }
    }
}
