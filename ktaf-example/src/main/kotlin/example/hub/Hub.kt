package example.hub

import example.hub.rooms.Clearing
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Region
import ktaf.utilities.RegionMaker
import ktaf.utilities.templates.RegionTemplate

internal class Hub : RegionTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Region {
        val regionMaker = RegionMaker(NAME, DESCRIPTION).also {
            it[0, 0, 0] = Clearing().instantiate(playableCharacter)
        }
        return regionMaker.make(0, 0, 0)
    }

    internal companion object {
        internal const val NAME = "Jungle"
        private const val DESCRIPTION = "A dense jungle, somewhere tropical."
    }
}
