package com.github.benpollarduk.ktaf.example

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Overworld
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.example.everglades.Everglades
import com.github.benpollarduk.ktaf.example.everglades.rooms.Outskirts
import com.github.benpollarduk.ktaf.example.everglades.rooms.Tunnel
import com.github.benpollarduk.ktaf.example.global.items.Sphere
import com.github.benpollarduk.ktaf.example.global.player.Player
import com.github.benpollarduk.ktaf.example.hub.Hub
import com.github.benpollarduk.ktaf.extensions.equalsExaminable
import com.github.benpollarduk.ktaf.io.IOConfiguration
import com.github.benpollarduk.ktaf.logic.Game
import com.github.benpollarduk.ktaf.logic.GameInformation
import com.github.benpollarduk.ktaf.logic.conditions.EndCheckResult
import com.github.benpollarduk.ktaf.utilities.templates.GameTemplate

/**
 * Provides an example [Game].
 */
public object ExampleGame : GameTemplate() {
    private fun determineIfGameIsComplete(game: Game): EndCheckResult {
        return if (Outskirts.NAME.equalsExaminable(game.overworld.currentRegion?.currentRoom)) {
            EndCheckResult(
                true,
                "Game Over",
                "You have reached the end of the game, thanks for playing!"
            )
        } else {
            EndCheckResult.notEnded
        }
    }

    private fun determineIfGameOver(game: Game): EndCheckResult {
        return if (!game.player.isAlive) {
            EndCheckResult(true, "Game Over", "You are dead.")
        } else {
            EndCheckResult.notEnded
        }
    }

    private fun populateHub(
        playableCharacter: PlayableCharacter,
        hub: Region,
        overworld: Overworld,
        otherRegions: List<Region>
    ) {
        val room = hub.currentRoom ?: return

        otherRegions.forEach { region ->
            room.addItem(Sphere(region, overworld).instantiate(playableCharacter))
        }
    }

    override fun instantiate(ioConfiguration: IOConfiguration): Game {
        val playableCharacter = Player().instantiate()
        val everglades = Everglades().instantiate(playableCharacter)
        val regions = listOf(everglades)
        val overworld = Overworld("Test Overworld", "This is a test overworld")
        val hub = Hub().instantiate(playableCharacter)
        populateHub(playableCharacter, hub, overworld, regions)
        overworld.addRegion(hub)

        regions.forEach {
            overworld.addRegion(it)
        }

        // add sphere to tunnel - requires an overworld
        everglades.findRoom(Tunnel.NAME)?.addItem(Sphere(hub, overworld).instantiate(playableCharacter))

        val about = "This is a short demo of ktaf made up from test chunks of games that were " +
            "build to test different features during development."

        return Game(
            GameInformation("ktav demo", about, about, "Ben Pollard"),
            playableCharacter,
            overworld,
            { determineIfGameIsComplete(it) },
            { determineIfGameOver(it) },
            ioConfiguration = ioConfiguration
        )
    }
}
