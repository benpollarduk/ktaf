package ktaf.rendering.frames.html

import ktaf.TestHelper
import ktaf.assets.Size
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.assets.locations.ViewPoint
import ktaf.interpretation.CommandHelp
import ktaf.logic.GameFactoryCreator
import ktaf.rendering.KeyType
import ktaf.rendering.frames.GridRoomMapBuilder
import ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class HtmlSceneFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HtmlSceneFrameBuilder(HtmlPageBuilder(), GridRoomMapBuilder(), Size(80, 50))
        val regionMaker = RegionMaker("", "")
        val startRoom = Room("Room", "This room has no description.", listOf(Exit(Direction.EAST)))
        regionMaker[0, 0, 0] = startRoom
        regionMaker[1, 0, 0] = Room("Bog of eternal stench", "", listOf(Exit(Direction.WEST)))
        val region = regionMaker.make()
        val viewPoint = ViewPoint(region)

        // When
        val result = builder.build(
            startRoom,
            viewPoint,
            PlayableCharacter("Test", "Test"),
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
            val game = TestHelper.getSimpleGameCreator()()
            val sceneFrameBuilder = HtmlSceneFrameBuilder(HtmlPageBuilder(), GridRoomMapBuilder(), Size(80, 50))
            val region = game.overworld.currentRegion ?: throw IllegalArgumentException("Region should not be null.")
            val room = region.currentRoom ?: throw IllegalArgumentException("Room should not be null.")
            val view = ViewPoint(region)

            // When
            sceneFrameBuilder.build(
                room,
                view,
                PlayableCharacter("", ""),
                "",
                GameFactoryCreator.defaultInterpreters.getContextualCommandHelp(game),
                KeyType.FULL
            )
        }
    }
}
