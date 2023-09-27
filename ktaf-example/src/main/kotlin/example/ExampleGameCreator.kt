package example

import example.player.Player
import ktaf.assets.locations.Overworld
import ktaf.assets.locations.Region
import ktaf.io.IOConfiguration
import ktaf.logic.EndCheckResult
import ktaf.logic.Game
import ktaf.logic.GameCreator
import ktaf.logic.OverworldCreator

/**
 * Provides an example [GameCreator].
 */
public object ExampleGameCreator {
    /**
     * Create a new instance of the example [GameCreator] using a [ioConfiguration].
     */
    public fun create(ioConfiguration: IOConfiguration): GameCreator {
        val player = Player().instantiate()

        val overworldCreator: OverworldCreator = {
            val regions = emptyList<Region>()
            Overworld("Test Overworld", "This is a test overworld").also {
                for (region in regions) {
                    it.addRegion(region)
                }
            }
        }

        val about = "This is a short demo of the BP.AdventureFramework made up from test chunks of games that were " +
            "build to test different features during development."

        return Game.create(
            "ktav dmo",
            about,
            about,
            "Ben Pollard",
            overworldCreator,
            { player },
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded },
            ioConfiguration = ioConfiguration
        )
    }
}
