package ktaf

import ktaf.assets.Item
import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.conversations.Conversation
import ktaf.conversations.Paragraph
import ktaf.conversations.Response
import ktaf.io.IOConfiguration
import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.logic.EndCheckResult
import ktaf.logic.Game
import ktaf.logic.GameCreator
import ktaf.utilities.OverworldMaker
import ktaf.utilities.RegionMaker

/**
 *  An object for providing objects for debugging purposes.
 */
internal object TestHelper {
    /**
     * Provides a simple instance of [GameCreator] with an optionally specified [ioConfiguration].
     */
    internal fun getSimpleGameCreator(ioConfiguration: IOConfiguration = AnsiConsoleConfiguration): GameCreator {
        val basicRoomDescription = "This is just a test room."
        val player = PlayableCharacter("Test Player", "This is a test player.")
        val regionMaker = RegionMaker("Test Region", "This is a test region.")
        regionMaker[1, 1, 1] = Room(
            "Test Room",
            basicRoomDescription,
            listOf(
                Exit(Direction.NORTH),
                Exit(Direction.SOUTH),
                Exit(Direction.EAST),
                Exit(Direction.WEST),
                Exit(Direction.UP),
                Exit(Direction.DOWN)
            )
        ).also { room ->
            room.addItem(Item("Beaver", "A beaver is on the floor!", true))
            room.addCharacter(NonPlayableCharacter("Person 1", "A quiet person."))
            room.addCharacter(
                NonPlayableCharacter("Person 2", "A chatty person.").also {
                    it.conversation = Conversation(
                        listOf(
                            Paragraph("Hello I'm a test."),
                            Paragraph("Here is a question?").also { paragraph ->
                                paragraph.responses = listOf(
                                    Response("Continue"),
                                    Response("Repeat", -1)
                                )
                            }
                        )
                    )
                }
            )
        }
        regionMaker[0, 1, 1] = Room(
            "West Room",
            basicRoomDescription,
            listOf(
                Exit(Direction.EAST)
            )
        )
        regionMaker[2, 1, 1] = Room(
            "East Room",
            basicRoomDescription,
            listOf(
                Exit(Direction.WEST)
            )
        )
        regionMaker[1, 2, 1] = Room(
            "North Room",
            basicRoomDescription,
            listOf(
                Exit(Direction.SOUTH)
            )
        )
        regionMaker[1, 0, 1] = Room(
            "South Room",
            basicRoomDescription,
            listOf(
                Exit(Direction.NORTH)
            )
        )
        regionMaker[1, 1, 2] = Room(
            "Top Room",
            basicRoomDescription,
            listOf(
                Exit(Direction.DOWN)
            )
        )
        regionMaker[1, 1, 0] = Room(
            "Bottom Room",
            basicRoomDescription,
            listOf(
                Exit(Direction.UP)
            )
        )
        val overworldMaker = OverworldMaker("Test Overworld", "This is a test overworld", listOf(regionMaker))
        return Game.create(
            "Test game",
            "This is a test game",
            "This is a test game",
            "Test author",
            { overworldMaker.make() },
            { player },
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded },
            ioConfiguration = ioConfiguration
        )
    }
}
