package com.github.benpollarduk.ktaf.assets.location

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.assets.interaction.InteractionEffect
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RoomTest {
    @Test
    fun `given no exits when add exit then one exit`() {
        // Given
        val room = Room("", "")

        // When
        room.addExit(Exit(Direction.WEST))
        val result = room.exits.size

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given one exit when remove exit then no exits`() {
        // Given
        val room = Room("", "", listOf(Exit(Direction.WEST)))

        // When
        room.removeExit(room.exits.first())
        val result = room.exits.size

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given one exit when has exit then return true`() {
        // Given
        val room = Room("", "", listOf(Exit(Direction.WEST)))

        // When
        val result = room.hasExit(Direction.WEST)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given one exit when does not have exit then return false`() {
        // Given
        val room = Room("", "", listOf(Exit(Direction.WEST)))

        // When
        val result = room.hasExit(Direction.EAST)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given no items when add item then one item`() {
        // Given
        val room = Room("", "")

        // When
        room.addItem(Item("", ""))
        val result = room.items.size

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given one item when remove item then no items`() {
        // Given
        val room = Room("", "")
        room.addItem(Item("", ""))

        // When
        room.removeItem(room.items.first())
        val result = room.items.size

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given one item when contains item then return true`() {
        // Given
        val room = Room("", "")
        room.addItem(Item("", ""))

        // When
        val result = room.containsItem(room.items.first())

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given one item when does not contain item then return false`() {
        // Given
        val room = Room("", "")

        // When
        val result = room.containsItem(Item("", ""))

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given one item when contains item with matching name then return true`() {
        // Given
        val room = Room("", "")
        room.addItem(Item("Test", ""))

        // When
        val result = room.containsItem("Test")

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given one item when does not contain item with matching name then return false`() {
        // Given
        val room = Room("", "")

        // When
        val result = room.containsItem("Test")

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given no characters when add character then one character`() {
        // Given
        val room = Room("", "")

        // When
        room.addCharacter(NonPlayableCharacter("", ""))
        val result = room.characters.size

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given one character when remove character then no characters`() {
        // Given
        val room = Room("", "")
        room.addCharacter(NonPlayableCharacter("", ""))

        // When
        room.removeCharacter(room.characters.first())
        val result = room.characters.size

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given one character when contains character then return true`() {
        // Given
        val room = Room("", "")
        room.addCharacter(NonPlayableCharacter("", ""))

        // When
        val result = room.containsCharacter(room.characters.first())

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given one character when does not contain character then return false`() {
        // Given
        val room = Room("", "")

        // When
        val result = room.containsCharacter(NonPlayableCharacter("", ""))

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given one character when contains character with matching name then return true`() {
        // Given
        val room = Room("", "")
        room.addCharacter(NonPlayableCharacter("Test", ""))

        // When
        val result = room.containsCharacter("Test")

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given one character when does not contain character with matching name then return false`() {
        // Given
        val room = Room("", "")

        // When
        val result = room.containsCharacter("Test")

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given one item when examine then result contains item name`() {
        // Given
        val room = Room("Room", "Room description")
        val item = Item("Item", "Item description")
        room.addItem(item)

        // When
        val result = room.examine().description

        // Then
        Assertions.assertTrue(result.contains("Item"))
    }

    @Test
    fun `given two items when examine then result contains both item names`() {
        // Given
        val room = Room("Room", "Room description")
        val item1 = Item("Item 1", "Item 1 description")
        val item2 = Item("Item 2", "Item 2 description")
        room.addItem(item1)
        room.addItem(item2)

        // When
        val result = room.examine().description

        // Then
        Assertions.assertTrue(result.contains("Item 1"))
        Assertions.assertTrue(result.contains("Item 2"))
    }

    @Test
    fun `given character in room when contains character then true returned`() {
        // Given
        val room = Room("Room", "Room description")
        val character = NonPlayableCharacter("NPC", "")
        room.addCharacter(character)

        // When
        val result = room.containsCharacter(character)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given character in room when contains character by name then true returned`() {
        // Given
        val room = Room("Room", "Room description")
        val character = NonPlayableCharacter("NPC", "")
        room.addCharacter(character)

        // When
        val result = room.containsCharacter(character.identifier.name)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given character in room when find character then character returned`() {
        // Given
        val room = Room("Room", "Room description")
        val character = NonPlayableCharacter("NPC", "")
        room.addCharacter(character)

        // When
        val result = room.findCharacter("NPC")

        // Then
        Assertions.assertEquals(character, result)
    }

    @Test
    fun `given character in room when contains interaction target by name then true returned`() {
        // Given
        val room = Room("Room", "Room description")
        val character = NonPlayableCharacter("NPC", "")
        room.addCharacter(character)

        // When
        val result = room.containsInteractionTarget(character.identifier.name)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given character in room when find interaction target then target returned`() {
        // Given
        val room = Room("Room", "Room description")
        val character = NonPlayableCharacter("NPC", "")
        room.addCharacter(character)

        // When
        val result = room.findInteractionTarget("NPC")

        // Then
        Assertions.assertEquals(character, result)
    }

    @Test
    fun `given no specified interaction when interact then no effect`() {
        // Given
        val room = Room("Room", "Room description")
        val item = Item("Item", "")
        room.addItem(item)

        // When
        val result = room.interact(item)

        // Then
        Assertions.assertEquals(InteractionEffect.NO_EFFECT, result.effect)
    }
}
