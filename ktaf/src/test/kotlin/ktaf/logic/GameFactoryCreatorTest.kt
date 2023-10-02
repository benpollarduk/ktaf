package ktaf.logic

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import ktaf.logic.conditions.EndCheckResult
import ktaf.utilities.OverworldMaker
import ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameFactoryCreatorTest {
    @Test
    fun `given create then not null`() {
        // Given
        val player = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        regionMaker[0, 0, 0] = Room("TestRoom", "")
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val result = GameFactoryCreator.create(
            GameInformation("", "", "", ""),
            { overworldMaker.make() },
            { player },
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // Then
        Assertions.assertNotNull(result)
    }
}
