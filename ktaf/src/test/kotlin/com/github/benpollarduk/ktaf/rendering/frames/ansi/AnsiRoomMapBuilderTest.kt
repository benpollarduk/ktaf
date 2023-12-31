package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.ViewPoint
import com.github.benpollarduk.ktaf.rendering.KeyType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiRoomMapBuilderTest {
    @Test
    fun `given test room with dynamic map when build then string with some length returned`() {
        // Given
        val builder = AnsiRoomMapBuilder()
        val room = Room(
            "",
            "",
            listOf(
                Exit(Direction.UP),
                Exit(Direction.EAST, true),
                Exit(Direction.SOUTH),
                Exit(Direction.NORTH)
            ),
            listOf(
                Item("", "")
            )
        )
        val gridStringBuilder = AnsiGridStringBuilder().also {
            it.resize(Size(75, 50))
        }

        // When
        builder.build(gridStringBuilder, room, ViewPoint.noView, KeyType.FULL, 0, 0)
        val result = AnsiGridTextFrame(gridStringBuilder, 0, 0).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.isNotEmpty())
    }

    @Test
    fun `given test room with key type full when build then string with some length returned`() {
        // Given
        val builder = AnsiRoomMapBuilder()
        val room = Room(
            "",
            "",
            listOf(
                Exit(Direction.UP, true),
                Exit(Direction.EAST),
                Exit(Direction.SOUTH, true),
                Exit(Direction.NORTH, true)
            ),
            listOf(
                Item("", "")
            )
        )
        val gridStringBuilder = AnsiGridStringBuilder().also {
            it.resize(Size(75, 50))
        }

        // When
        builder.build(gridStringBuilder, room, ViewPoint.noView, KeyType.FULL, 0, 0)
        val result = AnsiGridTextFrame(gridStringBuilder, 0, 0).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.isNotEmpty())
    }

    @Test
    fun `given test room with key type none when build then string with some length returned`() {
        // Given
        val builder = AnsiRoomMapBuilder()
        val room = Room(
            "",
            "",
            listOf(
                Exit(Direction.UP),
                Exit(Direction.WEST, true),
                Exit(Direction.DOWN),
                Exit(Direction.NORTH)
            )
        )
        val gridStringBuilder = AnsiGridStringBuilder().also {
            it.resize(Size(75, 50))
        }

        // When
        builder.build(gridStringBuilder, room, ViewPoint.noView, KeyType.NONE, 0, 0)
        val result = AnsiGridTextFrame(gridStringBuilder, 0, 0).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.isNotEmpty())
    }
}
