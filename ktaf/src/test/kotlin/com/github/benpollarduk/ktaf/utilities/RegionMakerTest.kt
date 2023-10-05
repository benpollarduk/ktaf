package com.github.benpollarduk.ktaf.utilities

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.RoomPosition
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("MaxLineLength")
class RegionMakerTest {
    @Test
    fun `given any empty list when convertToRoomMatrix then return empty matrix`() {
        // Given
        val list: List<RoomPosition> = emptyList()

        // When
        val result = RegionMaker.convertToRoomMatrix(list)

        // Then
        Assertions.assertTrue(result.isEmpty)
    }

    @Test
    fun `given one room position when convertToRoomMatrix then return non-empty matrix`() {
        // Given
        val list: List<RoomPosition> = listOf(RoomPosition(Room("", ""), 0, 0, 0))

        // When
        val result = RegionMaker.convertToRoomMatrix(list)

        // Then
        Assertions.assertFalse(result.isEmpty)
    }

    @Test
    fun `given region maker with no rooms when getting room positions then return empty list`() {
        // Given
        val regionMaker = RegionMaker("", "")

        // When
        val result = regionMaker.getRoomPositions()

        // Then
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `given region maker with one room when getting room positions then return list with one element`() {
        // Given
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 0, 0] = Room("", "")

        // When
        val result = regionMaker.getRoomPositions()

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given no room in specified position when can place room then return true`() {
        // Given
        val regionMaker = RegionMaker("", "")

        // When
        val result = regionMaker.canPlaceRoom(0, 0, 0)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a room in specified position when can place room then return false`() {
        // Given
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 0, 0] = Room("", "")

        // When
        val result = regionMaker.canPlaceRoom(0, 0, 0)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given 3 rooms in when make then return region with 3 rooms`() {
        // Given
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 0, 0] = Room("", "")
        regionMaker[1, 0, 0] = Room("", "")
        regionMaker[2, 0, 0] = Room("", "")

        // When
        val result = regionMaker.make()

        // Then
        Assertions.assertEquals(3, result.rooms)
    }

    @Test
    fun `given 2 adjoining rooms when one has an unlocked exit and the other has no exit make then unlocked linked exit is created`() {
        // Given
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 0, 0] = Room("", "", listOf(Exit(Direction.EAST)))
        regionMaker[1, 0, 0] = Room("", "")

        // When
        val region = regionMaker.make()
        val exit = region[1, 0, 0]?.findExit(Direction.WEST)

        // Then
        Assertions.assertNotNull(exit)
        Assertions.assertFalse(exit?.isLocked ?: true)
    }

    @Test
    fun `given 2 adjoining rooms when one has a locked exit and the other has no exit make then locked linked exit is created`() {
        // Given
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 0, 0] = Room("", "", listOf(Exit(Direction.EAST, true)))
        regionMaker[1, 0, 0] = Room("", "")

        // When
        val region = regionMaker.make()
        val exit = region[1, 0, 0]?.findExit(Direction.WEST)

        // Then
        Assertions.assertNotNull(exit)
        Assertions.assertTrue(exit?.isLocked ?: false)
    }
}
