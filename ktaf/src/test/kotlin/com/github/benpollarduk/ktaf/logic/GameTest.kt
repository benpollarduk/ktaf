package com.github.benpollarduk.ktaf.logic

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.logic.conditions.EndCheckResult
import com.github.benpollarduk.ktaf.utilities.OverworldMaker
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameTest {
    @Test
    fun `given blank game with player called testplayer when find interaction target then find player`() {
        // Given
        val game = GameTestHelper.getBlankGame()

        // When
        val result = game.findInteractionTarget("TestPlayer")

        // Then
        Assertions.assertTrue(result is PlayableCharacter)
    }

    @Test
    fun `given blank game with room called testroom when find interaction target then find room`() {
        // Given
        val game = GameTestHelper.getBlankGame()

        // When
        val result = game.findInteractionTarget("TestRoom")

        // Then
        Assertions.assertTrue(result is Room)
    }

    @Test
    fun `given no active converser when start conversation called then active converser not null`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val converser = NonPlayableCharacter("", "")

        // When
        game.startConversation(converser)
        val result = game.activeConverser

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given active converser when end conversation called then active converser null`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val converser = NonPlayableCharacter("", "")
        game.startConversation(converser)

        // When
        game.endConversation()
        val result = game.activeConverser

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given create then not null`() {
        // Given
        val player = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        regionMaker[0, 0, 0] = Room("TestRoom", "")
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val result = Game(
            GameInformation("", "", "", ""),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given player when find interaction target then return player`() {
        // Given
        val player = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        regionMaker[0, 0, 0] = Room("TestRoom", "")
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val game = Game(
            GameInformation("", "", "", ""),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // When
        val result = game.findInteractionTarget(player.identifier.name)

        // Then
        Assertions.assertEquals(player, result)
    }

    @Test
    fun `given player item when find interaction target then return item`() {
        // Given
        val item = Item("TestItem", "")
        val player = PlayableCharacter("TestPlayer", "")
        player.acquireItem(item)
        val regionMaker = RegionMaker("TestRegion", "")
        regionMaker[0, 0, 0] = Room("TestRoom", "")
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val game = Game(
            GameInformation("", "", "", ""),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // When
        val result = game.findInteractionTarget(item.identifier.name)

        // Then
        Assertions.assertEquals(item, result)
    }

    @Test
    fun `given room item when find interaction target then return item`() {
        // Given
        val item = Item("TestItem", "")
        val player = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        val room = Room("TestRoom", "")
        room.addItem(item)
        regionMaker[0, 0, 0] = room
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val game = Game(
            GameInformation("", "", "", ""),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // When
        val result = game.findInteractionTarget(item.identifier.name)

        // Then
        Assertions.assertEquals(item, result)
    }

    @Test
    fun `given room character when find interaction target then return character`() {
        // Given
        val npc = NonPlayableCharacter("TestCharacter", "")
        val player = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        val room = Room("TestRoom", "")
        room.addCharacter(npc)
        regionMaker[0, 0, 0] = room
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val game = Game(
            GameInformation("", "", "", ""),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // When
        val result = game.findInteractionTarget(npc.identifier.name)

        // Then
        Assertions.assertEquals(npc, result)
    }

    @Test
    fun `given room when find interaction target then return room`() {
        // Given
        val player = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        val room = Room("TestRoom", "")
        regionMaker[0, 0, 0] = room
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val game = Game(
            GameInformation("", "", "", ""),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // When
        val result = game.findInteractionTarget(room.identifier.name)

        // Then
        Assertions.assertEquals(room, result)
    }

    @Test
    fun `given display transition then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val player = PlayableCharacter("TestPlayer", "")
            val regionMaker = RegionMaker("TestRegion", "")
            regionMaker[0, 0, 0] = Room("TestRoom", "")
            val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
            val game = Game(
                GameInformation("", "", "", ""),
                player,
                overworldMaker.make(),
                { EndCheckResult.notEnded },
                { EndCheckResult.notEnded }
            )

            // When
            game.displayTransition("Title", "Message")
        }
    }

    @Test
    fun `given change player when player2 then player is player2`() {
// Given
        val player = PlayableCharacter("TestPlayer", "")
        val player2 = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        regionMaker[0, 0, 0] = Room("TestRoom", "")
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val game = Game(
            GameInformation("", "", "", ""),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // When
        game.changePlayer(player2)

        // Then
        Assertions.assertEquals(player2, game.player)
    }
}
