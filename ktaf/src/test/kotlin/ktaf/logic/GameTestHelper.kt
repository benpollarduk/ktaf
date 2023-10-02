package ktaf.logic

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import ktaf.logic.conditions.EndCheckResult
import ktaf.utilities.OverworldMaker
import ktaf.utilities.RegionMaker

/**
 * Provides a test helper for the [Game] class.
 */
internal object GameTestHelper {
    /**
     * Get a blank game.
     */
    internal fun getBlankGame(): Game {
        val player = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        regionMaker[0, 0, 0] = Room("TestRoom", "")
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        return Game(
            GameInformation("", "", "", ""),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )
    }
}
