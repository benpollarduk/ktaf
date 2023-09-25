package ktaf.logic

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import ktaf.utilities.OverworldMaker
import ktaf.utilities.RegionMaker
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
        val result = Game.create(
            "",
            "",
            "",
            "",
            { overworldMaker.make() },
            { player },
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // Then
        Assertions.assertNotNull(result)
    }
}
