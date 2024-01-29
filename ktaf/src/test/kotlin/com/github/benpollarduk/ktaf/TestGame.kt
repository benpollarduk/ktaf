package com.github.benpollarduk.ktaf

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.conversations.Conversation
import com.github.benpollarduk.ktaf.conversations.Paragraph
import com.github.benpollarduk.ktaf.conversations.Response
import com.github.benpollarduk.ktaf.conversations.instructions.Previous
import com.github.benpollarduk.ktaf.io.IOConfiguration
import com.github.benpollarduk.ktaf.logic.Game
import com.github.benpollarduk.ktaf.logic.GameInformation
import com.github.benpollarduk.ktaf.logic.conditions.EndCheckResult
import com.github.benpollarduk.ktaf.utilities.OverworldMaker
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import com.github.benpollarduk.ktaf.utilities.templates.GameTemplate

/**
 *  An object for providing a test [Game] for debugging purposes.
 */
internal object TestGame : GameTemplate() {
    private fun createTestRegion(): RegionMaker {
        val basicRoomDescription = "This is just a test room."
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
                                    Response("Repeat", Previous())
                                )
                            }
                        )
                    )
                }
            )
        }
        regionMaker[0, 1, 1] = Room("West Room", basicRoomDescription, listOf(Exit(Direction.EAST)))
        regionMaker[2, 1, 1] = Room("East Room", basicRoomDescription, listOf(Exit(Direction.WEST)))
        regionMaker[1, 2, 1] = Room("North Room", basicRoomDescription, listOf(Exit(Direction.SOUTH)))
        regionMaker[1, 0, 1] = Room("South Room", basicRoomDescription, listOf(Exit(Direction.NORTH)))
        regionMaker[1, 1, 2] = Room("Top Room", basicRoomDescription, listOf(Exit(Direction.DOWN)))
        regionMaker[1, 1, 0] = Room("Bottom Room", basicRoomDescription, listOf(Exit(Direction.UP)))
        return regionMaker
    }

    override fun instantiate(ioConfiguration: IOConfiguration): Game {
        val player = PlayableCharacter("Test Player", "This is a test player.")
        val regionMaker = createTestRegion()
        val overworldMaker = OverworldMaker(
            "Test Overworld",
            "This is a test overworld",
            listOf(regionMaker)
        )
        return Game(
            GameInformation(
                "Test game",
                "This is a test game",
                "This is a test game",
                "Test author"
            ),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded },
            ioConfiguration = ioConfiguration
        )
    }
}
