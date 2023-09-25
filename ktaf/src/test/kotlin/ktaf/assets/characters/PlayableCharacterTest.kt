package ktaf.assets.characters

import ktaf.assets.Item
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PlayableCharacterTest {
    @Test
    fun `given a default instance when not killed then isAlive is true`() {
        // Given
        val character = PlayableCharacter("", "")

        // When
        val result = character.isAlive

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a default instance when kill has been called then isAlive is false`() {
        // Given
        val character = PlayableCharacter("", "")

        // When
        character.kill()
        val result = character.isAlive

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a default instance when checking for an item it doesn't have then false is returned`() {
        // Given
        val character = PlayableCharacter("", "")

        // When
        val result = character.hasItem(Item("", ""))

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a default instance when checking for an item it has been given then true is returned`() {
        // Given
        val character = PlayableCharacter("", "")
        val item = Item("", "")

        // When
        character.acquireItem(item)
        val result = character.hasItem(item)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a default instance when checking for an item it has been given and then dequired then false is returned`() {
        // Given
        val character = PlayableCharacter("", "")
        val item = Item("", "")

        // When
        character.acquireItem(item)
        character.decquireItem(item)
        val result = character.hasItem(item)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a default instance when finding an item it doesn't have then null is returned`() {
        // Given
        val character = PlayableCharacter("", "")

        // When
        val result = character.findItem("")

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given a default instance when finding a visible item it has then instance is returned`() {
        // Given
        val character = PlayableCharacter("", "", listOf(Item("Test", "")))

        // When
        val result = character.findItem("Test")

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given a default instance when finding an invisible item it has then null is returned`() {
        // Given
        val item = Item("Test", "").apply {
            isPlayerVisible = false
        }
        val character = PlayableCharacter("", "", listOf(item))

        // When
        val result = character.findItem("Test")

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given a default instance when finding an invisible item it has and including invisible items then instance is returned`() {
        // Given
        val item = Item("Test", "").apply {
            isPlayerVisible = false
        }
        val character = PlayableCharacter("", "", listOf(item))

        // When
        val result = character.findItem("Test", true)

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given a default instance when giving an item it doesn't have then false is returned`() {
        // Given
        val character = PlayableCharacter("", "")
        val item = Item("", "")
        val otherCharacter = NonPlayableCharacter("", "")

        // When
        val result = character.give(item, otherCharacter)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a default instance when giving an item it has then true is returned`() {
        // Given
        val item = Item("", "")
        val character = PlayableCharacter("", "", listOf(item))
        val otherCharacter = NonPlayableCharacter("", "")

        // When
        val result = character.give(item, otherCharacter)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a default instance when giving an item it has then item is dequired`() {
        // Given
        val item = Item("Test", "")
        val character = PlayableCharacter("", "", listOf(item))
        val otherCharacter = NonPlayableCharacter("", "")

        // When
        character.give(item, otherCharacter)
        val result = character.hasItem(item)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a default instance when giving an item it has then other character has item`() {
        // Given
        val item = Item("Test", "")
        val character = PlayableCharacter("", "", listOf(item))
        val otherCharacter = NonPlayableCharacter("", "")

        // When
        character.give(item, otherCharacter)
        val result = otherCharacter.hasItem(item)

        // Then
        Assertions.assertTrue(result)
    }
}
