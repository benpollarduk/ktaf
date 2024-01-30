package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.TestGame
import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.ViewPoint
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import com.github.benpollarduk.ktaf.io.configurations.AnsiConsoleConfiguration
import com.github.benpollarduk.ktaf.logic.Game
import com.github.benpollarduk.ktaf.rendering.KeyType
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class AnsiSceneFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiSceneFrameBuilder(AnsiGridStringBuilder(), AnsiRoomMapBuilder(), Size(80, 50))
        val regionMaker = RegionMaker("", "")
        val startRoom = Room("Room", "This room has no description.", listOf(Exit(Direction.EAST)))
        regionMaker[0, 0, 0] = startRoom
        regionMaker[1, 0, 0] = Room("Bog of eternal stench", "", listOf(Exit(Direction.WEST)))
        val region = regionMaker.make()
        val viewPoint = ViewPoint(region)
        val player = PlayableCharacter("Test", "Test")
        player.acquireItem(Item("Test", "Test"))
        player.attributes.add("Test", 1)

        // When
        val result = builder.build(
            startRoom,
            viewPoint,
            player,
            "",
            listOf(CommandHelp("Test", "Test command.")),
            KeyType.DYNAMIC
        ).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }

    @Test
    fun `given complex game when build then no exception thrown`() {
        assertDoesNotThrow {
            // Given
            val game = TestGame.instantiate(AnsiConsoleConfiguration)
            val sceneFrameBuilder = AnsiSceneFrameBuilder(AnsiGridStringBuilder(), AnsiRoomMapBuilder(), Size(80, 50))
            val region = game.overworld.currentRegion ?: throw IllegalArgumentException("Region should not be null.")
            val room = region.currentRoom ?: throw IllegalArgumentException("Room should not be null.")
            val view = ViewPoint(region)

            // When
            sceneFrameBuilder.build(
                room,
                view,
                PlayableCharacter("", ""),
                "",
                Game.defaultInterpreters.getContextualCommandHelp(game),
                KeyType.FULL
            )
        }
    }
}
