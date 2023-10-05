package com.github.benpollarduk.ktaf.assets.characters

import com.github.benpollarduk.ktaf.assets.Item
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("MaxLineLength")
class NonPlayableCharacterTest {
    @Test
    fun `given a default instance when not killed then isAlive is true`() {
        // Given
        val character = NonPlayableCharacter("", "")

        // When
        val result = character.isAlive

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a default instance when kill has been called then isAlive is false`() {
        // Given
        val character = NonPlayableCharacter("", "")

        // When
        character.kill()
        val result = character.isAlive

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a default instance when checking for an item it doesn't have then false is returned`() {
        // Given
        val character = NonPlayableCharacter("", "")

        // When
        val result = character.hasItem(Item("", ""))

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a default instance when checking for an item it has been given then true is returned`() {
        // Given
        val character = NonPlayableCharacter("", "")
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
        val character = NonPlayableCharacter("", "")
        val item = Item("", "")

        // When
        character.acquireItem(item)
        character.decquireItem(item)
        val result = character.hasItem(item)

        // Then
        Assertions.assertFalse(result)
    }
}
