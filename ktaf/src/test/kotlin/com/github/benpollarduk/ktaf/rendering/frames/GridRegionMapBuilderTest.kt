package com.github.benpollarduk.ktaf.rendering.frames

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GridRegionMapBuilderTest {
    @Test
    fun `given single floor when build then string with some length returned`() {
        // Given
        val builder = GridRegionMapBuilder()
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 1, 1] = Room("", "", listOf(Exit(Direction.EAST)))
        regionMaker[1, 1, 1] = Room(
            "",
            "",
            listOf(
                Exit(Direction.EAST),
                Exit(Direction.WEST),
                Exit(Direction.NORTH),
                Exit(Direction.SOUTH),
                Exit(Direction.UP),
                Exit(Direction.DOWN)
            )
        )
        regionMaker[2, 1, 1] = Room("", "", listOf(Exit(Direction.WEST)))
        regionMaker[0, 0, 1] = Room("", "", listOf(Exit(Direction.SOUTH)))
        regionMaker[0, 2, 1] = Room("", "", listOf(Exit(Direction.NORTH)))
        regionMaker[1, 2, 0] = Room("", "", listOf(Exit(Direction.UP)))
        regionMaker[1, 2, 2] = Room("", "", listOf(Exit(Direction.DOWN)))
        val start = regionMaker[1, 1, 1] ?: Room("", "")
        val region = regionMaker.make(start)
        val gridStringBuilder = GridStringBuilder().also {
            it.resize(Size(80, 50))
        }

        // When
        val result = builder.build(gridStringBuilder, region, 0, 0, 80, 50).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }

    @Test
    fun `given multiple floors with locked doors when build then string with some length returned`() {
        // Given
        val builder = GridRegionMapBuilder()
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 1, 1] = Room("", "", listOf(Exit(Direction.EAST, true)))
        regionMaker[1, 1, 1] = Room(
            "",
            "",
            listOf(
                Exit(Direction.EAST, true),
                Exit(Direction.WEST, true),
                Exit(Direction.NORTH, true),
                Exit(Direction.SOUTH, true),
                Exit(Direction.UP, true),
                Exit(Direction.DOWN, true)
            )
        )
        regionMaker[2, 1, 1] = Room("", "", listOf(Exit(Direction.WEST, true)))
        regionMaker[0, 0, 1] = Room("", "", listOf(Exit(Direction.SOUTH, true)))
        regionMaker[0, 2, 1] = Room("", "", listOf(Exit(Direction.NORTH, true)))
        regionMaker[1, 2, 0] = Room("", "", listOf(Exit(Direction.UP, true)))
        regionMaker[1, 2, 2] = Room("", "", listOf(Exit(Direction.DOWN, true)))
        val start = regionMaker[1, 1, 1] ?: Room("", "")
        val region = regionMaker.make(start)
        val gridStringBuilder = GridStringBuilder().also {
            it.resize(Size(80, 50))
        }

        // When
        val result = builder.build(gridStringBuilder, region, 0, 0, 80, 50).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
