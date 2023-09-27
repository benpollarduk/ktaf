package ktaf.helpers

import ktaf.TestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestHelperTest {
    @Test
    fun `given get simple debug then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            TestHelper.getSimpleGameCreator()
        }
    }
}
