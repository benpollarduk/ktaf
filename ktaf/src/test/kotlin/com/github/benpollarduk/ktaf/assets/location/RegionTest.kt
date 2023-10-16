package com.github.benpollarduk.ktaf.assets.location

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.assets.locations.Room
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RegionTest {
    @Test
    fun `given a region with no rooms when getting position of room then return null`() {
        // Given
        val region = Region("", "")

        // When
        val result = region.getPositionOfRoom(Room.empty)

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given a region with one room when getting position of room then return correct instance`() {
        // Given
        val region = Region("", "")
        val room = Room("", "")
        region.addRoom(room, 0, 0, 0)

        // When
        val result = region.getPositionOfRoom(room)

        // Then
        Assertions.assertEquals(room, result?.room)
    }

    @Test
    fun `given a region with one room when getting position of room outside region then return null`() {
        // Given
        val region = Region("", "")
        val room = Room("", "")
        region.addRoom(room, 0, 0, 0)

        // When
        val result = region.getPositionOfRoom(Room("Test", ""))

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given a region with no rooms when adding a room then return true`() {
        // Given
        val region = Region("", "")
        val room = Room("", "")

        // When
        val result = region.addRoom(room, 0, 0, 0)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a region with one room when adding a room at the same position then return false`() {
        // Given
        val region = Region("", "")
        val room = Room("", "")
        region.addRoom(room, 0, 0, 0)

        // When
        val result = region.addRoom(Room.empty, 0, 0, 0)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a region with one room when adding a duplicate room at a different position then return false`() {
        // Given
        val region = Region("", "")
        val room = Room("", "")
        region.addRoom(room, 0, 0, 0)

        // When
        val result = region.addRoom(room, 1, 1, 1)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a region with one room then rooms is 1`() {
        // Given
        val region = Region("", "")
        val room = Room("", "")
        region.addRoom(room, 0, 0, 0)

        // When
        val result = region.rooms

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given a region with two rooms then rooms is 2`() {
        // Given
        val region = Region("", "")
        val room1 = Room("", "")
        val room2 = Room("", "")
        region.addRoom(room1, 0, 0, 0)
        region.addRoom(room2, 1, 0, 0)

        // When
        val result = region.rooms

        // Then
        Assertions.assertEquals(2, result)
    }

    @Test
    fun `given a region with one room when jumping to that room then return true`() {
        // Given
        val region = Region("", "")
        val room1 = Room("", "")
        region.addRoom(room1, 0, 0, 0)

        // When
        val result = region.jumpToRoom(room1)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a region with one room when jumping to a different location then return false`() {
        // Given
        val region = Region("", "")
        val room1 = Room("", "")
        region.addRoom(room1, 0, 0, 0)

        // When
        val result = region.jumpToRoom("no-room")

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given position of 000 when getting next position north then return 010`() {
        // Given
        val x = 0
        val y = 0
        val z = 0
        val direction = Direction.NORTH

        // When
        val result = Region.nextPosition(x, y, z, direction)

        // Then
        Assertions.assertEquals(0, result.x)
        Assertions.assertEquals(1, result.y)
        Assertions.assertEquals(0, result.z)
    }

    @Test
    fun `given position of 010 when getting next position south then return 000`() {
        // Given
        val x = 0
        val y = 1
        val z = 0
        val direction = Direction.SOUTH

        // When
        val result = Region.nextPosition(x, y, z, direction)

        // Then
        Assertions.assertEquals(0, result.x)
        Assertions.assertEquals(0, result.y)
        Assertions.assertEquals(0, result.z)
    }

    @Test
    fun `given position of 000 when getting next position east then return 100`() {
        // Given
        val x = 0
        val y = 0
        val z = 0
        val direction = Direction.EAST

        // When
        val result = Region.nextPosition(x, y, z, direction)

        // Then
        Assertions.assertEquals(1, result.x)
        Assertions.assertEquals(0, result.y)
        Assertions.assertEquals(0, result.z)
    }

    @Test
    fun `given position of 100 when getting next position west then return 000`() {
        // Given
        val x = 1
        val y = 0
        val z = 0
        val direction = Direction.WEST

        // When
        val result = Region.nextPosition(x, y, z, direction)

        // Then
        Assertions.assertEquals(0, result.x)
        Assertions.assertEquals(0, result.y)
        Assertions.assertEquals(0, result.z)
    }

    @Test
    fun `given position of 000 when getting next position up then return 001`() {
        // Given
        val x = 0
        val y = 0
        val z = 0
        val direction = Direction.UP

        // When
        val result = Region.nextPosition(x, y, z, direction)

        // Then
        Assertions.assertEquals(0, result.x)
        Assertions.assertEquals(0, result.y)
        Assertions.assertEquals(1, result.z)
    }

    @Test
    fun `given position of 001 when getting next position down then return 000`() {
        // Given
        val x = 0
        val y = 0
        val z = 1
        val direction = Direction.DOWN

        // When
        val result = Region.nextPosition(x, y, z, direction)

        // Then
        Assertions.assertEquals(0, result.x)
        Assertions.assertEquals(0, result.y)
        Assertions.assertEquals(0, result.z)
    }

    @Test
    fun `given no adjoining room when get adjoining room then return null`() {
        // Given
        val region = Region("", "")
        region.addRoom(Room("", ""), 0, 0, 0)

        // When
        val result = region.getAdjoiningRoom(Direction.SOUTH)

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given adjoining room when get adjoining room then return not null`() {
        // Given
        val region = Region("", "")
        region.addRoom(Room("", ""), 0, 0, 0)
        region.addRoom(Room("", ""), 0, 1, 0)

        // When
        val result = region.getAdjoiningRoom(Direction.NORTH)

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given adjoining room when get adjoining room with specified room then return correct instance`() {
        // Given
        val region = Region("", "")
        val room1 = Room("", "")
        val room2 = Room("", "")
        region.addRoom(room1, 0, 0, 0)
        region.addRoom(room2, 0, 1, 0)

        // When
        val result = region.getAdjoiningRoom(Direction.SOUTH, room2)

        // Then
        Assertions.assertEquals(room1, result)
    }

    @Test
    fun `given no available move when get move then return false`() {
        // Given
        val region = Region("", "")
        val room1 = Room("", "")
        val room2 = Room("", "")
        region.addRoom(room1, 0, 0, 0)
        region.addRoom(room2, 0, 1, 0)

        // When
        val result = region.move(Direction.SOUTH)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given available move when get move then return currentRoom is new room`() {
        // Given
        val region = Region("", "")
        val room1 = Room("", "", listOf(Exit(Direction.NORTH)))
        val room2 = Room("", "", listOf(Exit(Direction.SOUTH)))
        region.addRoom(room1, 0, 0, 0)
        region.addRoom(room2, 0, 1, 0)

        // When
        region.move(Direction.NORTH)

        // Then
        Assertions.assertEquals(room2, region.currentRoom)
    }

    @Test
    fun `given two rooms when set start room to second room then return currentRoom is second room`() {
        // Given
        val region = Region("", "")
        val room1 = Room("", "")
        val room2 = Room("", "")
        region.addRoom(room1, 0, 0, 0)
        region.addRoom(room2, 0, 1, 0)

        // When
        region.setStartRoom(room2)

        // Then
        Assertions.assertEquals(room2, region.currentRoom)
    }

    @Test
    fun `given two rooms when set start room to second room with coordinates then return currentRoom is second room`() {
        // Given
        val region = Region("", "")
        val room1 = Room("", "")
        val room2 = Room("", "")
        region.addRoom(room1, 0, 0, 0)
        region.addRoom(room2, 0, 1, 0)

        // When
        region.setStartRoom(room2)

        // Then
        Assertions.assertEquals(room2, region.currentRoom)
    }

    @Test
    fun `given two adjoining rooms with connected locked exits when when unlocking pair then both exits unlocked`() {
        // Given
        val region = Region("", "")
        val room1 = Room("", "", listOf(Exit(Direction.NORTH, true)))
        val room2 = Room("", "", listOf(Exit(Direction.SOUTH, true)))
        region.addRoom(room1, 0, 0, 0)
        region.addRoom(room2, 0, 1, 0)

        // When
        region.unlockExitPair(Direction.NORTH)

        // Then
        Assertions.assertFalse(room1[Direction.NORTH]?.isLocked ?: true)
        Assertions.assertFalse(room2[Direction.SOUTH]?.isLocked ?: true)
    }

    @Test
    fun `given contains room when find room then not null returned`() {
        // Given
        val region = Region("", "")
        val roomName = "room1"
        val room1 = Room(roomName, "")
        region.addRoom(room1, 0, 0, 0)

        // When
        val result = region.findRoom(roomName)

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given contains room when find room then expected room returned`() {
        // Given
        val region = Region("", "")
        val roomName = "room1"
        val room1 = Room(roomName, "")
        region.addRoom(room1, 0, 0, 0)

        // When
        val result = region.findRoom(roomName)

        // Then
        Assertions.assertEquals(room1, result)
    }

    @Test
    fun `given doesnt contain room when find room then null returned`() {
        // Given
        val region = Region("", "")
        val room1 = Room("room1", "")
        region.addRoom(room1, 0, 0, 0)

        // When
        val result = region.findRoom("room2")

        // Then
        Assertions.assertNull(result)
    }
}
