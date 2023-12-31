package com.github.benpollarduk.ktaf.assets.location

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.ViewPoint
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ViewPointTest {
    @Test
    fun `given a viewpoint of a room with NESW surrounding rooms then NESW viewpoint not null`() {
        val regionMaker = RegionMaker("", "").also {
            it[1, 0, 0] = Room("", "", listOf(Exit(Direction.NORTH)))
            it[0, 1, 0] = Room("", "", listOf(Exit(Direction.EAST)))
            it[1, 1, 0] = Room(
                "Test",
                "",
                listOf(
                    Exit(Direction.NORTH),
                    Exit(Direction.EAST),
                    Exit(Direction.SOUTH),
                    Exit(Direction.WEST)
                )
            )
            it[2, 1, 0] = Room("", "", listOf(Exit(Direction.WEST)))
            it[1, 2, 0] = Room("", "", listOf(Exit(Direction.SOUTH)))
        }
        val region = regionMaker.make("Test")
        val result = ViewPoint(region)
        Assertions.assertNotNull(result[Direction.NORTH])
        Assertions.assertNotNull(result[Direction.EAST])
        Assertions.assertNotNull(result[Direction.SOUTH])
        Assertions.assertNotNull(result[Direction.WEST])
    }

    @Test
    fun `given a viewpoint of a room with one surrounding room then one viewpoint not null`() {
        val regionMaker = RegionMaker("", "").also {
            it[1, 0, 0] = Room("Test", "", listOf(Exit(Direction.NORTH)))
            it[0, 1, 0] = Room("", "", listOf(Exit(Direction.EAST)))
            it[1, 1, 0] = Room(
                "",
                "",
                listOf(
                    Exit(Direction.NORTH),
                    Exit(Direction.EAST),
                    Exit(Direction.SOUTH),
                    Exit(Direction.WEST)
                )
            )
            it[2, 1, 0] = Room("", "", listOf(Exit(Direction.WEST)))
            it[1, 2, 0] = Room("", "", listOf(Exit(Direction.SOUTH)))
        }
        val region = regionMaker.make("Test")
        val result = ViewPoint(region)
        Assertions.assertNotNull(result[Direction.NORTH])
        Assertions.assertNull(result[Direction.EAST])
        Assertions.assertNull(result[Direction.SOUTH])
        Assertions.assertNull(result[Direction.WEST])
    }

    @Test
    fun `given a viewpoint of a room with one surrounding room when getting any then return true`() {
        val regionMaker = RegionMaker("", "").also {
            it[1, 0, 0] = Room("Test", "", listOf(Exit(Direction.NORTH)))
            it[0, 1, 0] = Room("", "", listOf(Exit(Direction.EAST)))
            it[1, 1, 0] = Room(
                "",
                "",
                listOf(
                    Exit(Direction.NORTH),
                    Exit(Direction.EAST),
                    Exit(Direction.SOUTH),
                    Exit(Direction.WEST)
                )
            )
            it[2, 1, 0] = Room("", "", listOf(Exit(Direction.WEST)))
            it[1, 2, 0] = Room("", "", listOf(Exit(Direction.SOUTH)))
        }
        val region = regionMaker.make("Test")
        val result = ViewPoint(region).any
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a viewpoint of a room with no surroundings room when getting any then return false`() {
        val regionMaker = RegionMaker("", "").also {
            it[0, 0, 0] = Room("Test", "")
        }
        val region = regionMaker.make("Test")
        val result = ViewPoint(region).any
        Assertions.assertFalse(result)
    }
}
