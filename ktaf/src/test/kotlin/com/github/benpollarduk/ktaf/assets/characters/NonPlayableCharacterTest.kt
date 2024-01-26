package com.github.benpollarduk.ktaf.assets.characters

import com.github.benpollarduk.ktaf.assets.Examination
import com.github.benpollarduk.ktaf.assets.ExaminationResult
import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.Interaction
import com.github.benpollarduk.ktaf.assets.interaction.InteractionEffect
import com.github.benpollarduk.ktaf.assets.interaction.InteractionResult
import com.github.benpollarduk.ktaf.conversations.Conversation
import com.github.benpollarduk.ktaf.extensions.toDescription
import com.github.benpollarduk.ktaf.extensions.toIdentifier
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

    @Test
    fun `given default instance with explicit examination then explicit examination is used`() {
        // Given
        val examination: Examination = { _ ->
            ExaminationResult("ABC")
        }
        val character = NonPlayableCharacter(
            "".toIdentifier(),
            "".toDescription(),
            Conversation.empty,
            false,
            examination
        )

        // When
        val result = character.examine()

        // Then
        Assertions.assertEquals("ABC", result.description)
    }

    @Test
    fun `given default instance with explicit interaction then explicit interaction is used`() {
        // Given
        val item = Item("", "")
        val examination: Examination = { _ ->
            ExaminationResult("ABC")
        }
        val interaction: Interaction = { i ->
            InteractionResult(InteractionEffect.ITEM_USED_UP, i, "XYZ")
        }
        val character = NonPlayableCharacter(
            "".toIdentifier(),
            "".toDescription(),
            Conversation.empty,
            false,
            examination,
            interaction
        )

        // When
        val result = character.interact(item)

        // Then
        Assertions.assertEquals("XYZ", result.description)
    }
}
