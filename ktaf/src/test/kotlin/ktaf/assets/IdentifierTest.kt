package ktaf.assets

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class IdentifierTest {

    @Test
    fun `given empty string as name when calling equals with null then return false`() {
        // Given
        val identifier = Identifier("")

        // When
        val result = identifier.equals(null)

        // Then
        assertFalse(result)
    }

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
}
