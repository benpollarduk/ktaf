package ktaf.logic

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
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
}
