package ktaf.rendering.frames

import ktaf.assets.Item
import ktaf.assets.Size
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.assets.locations.ViewPoint
import ktaf.rendering.KeyType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GridRoomMapBuilderTest {
    @Test
    fun `given test room with dynamic map when build then string with some length returned`() {
        // Given
        val builder = GridRoomMapBuilder()
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
        val gridStringBuilder = GridStringBuilder().also {
            it.resize(Size(75, 50))
        }

        // When
        builder.build(gridStringBuilder, room, ViewPoint.noView, KeyType.FULL, 0, 0)
        val result = gridStringBuilder.toString()
        print(result)

        // Then
        Assertions.assertTrue(result.isNotEmpty())
    }

    @Test
    fun `given test room with key type full when build then string with some length returned`() {
        // Given
        val builder = GridRoomMapBuilder()
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
        val gridStringBuilder = GridStringBuilder().also {
            it.resize(Size(75, 50))
        }

        // When
        builder.build(gridStringBuilder, room, ViewPoint.noView, KeyType.FULL, 0, 0)
        val result = gridStringBuilder.toString()
        print(result)

        // Then
        Assertions.assertTrue(result.isNotEmpty())
    }

    @Test
    fun `given test room with key type none when build then string with some length returned`() {
        // Given
        val builder = GridRoomMapBuilder()
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
        val gridStringBuilder = GridStringBuilder().also {
            it.resize(Size(75, 50))
        }

        // When
        builder.build(gridStringBuilder, room, ViewPoint.noView, KeyType.NONE, 0, 0)
        val result = gridStringBuilder.toString()
        print(result)

        // Then
        Assertions.assertTrue(result.isNotEmpty())
    }
}
