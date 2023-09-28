package example.everglades

import example.everglades.rooms.Cave
import example.everglades.rooms.CaveMouth
import example.everglades.rooms.ForestEntrance
import example.everglades.rooms.ForestFloor
import example.everglades.rooms.GreatWesternOcean
import example.everglades.rooms.InnerCave
import example.everglades.rooms.Outskirts
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Region
import ktaf.utilities.RegionMaker
import ktaf.utilities.templates.RegionTemplate

internal class Everglades : RegionTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Region {
        val regionMaker = RegionMaker(NAME, DESCRIPTION).also {
            it[2, 0, 0] = ForestEntrance().instantiate(playableCharacter)
            it[2, 1, 0] = ForestFloor().instantiate(playableCharacter)
            it[2, 2, 0] = CaveMouth().instantiate(playableCharacter)
            it[1, 2, 0] = GreatWesternOcean().instantiate(playableCharacter)
            it[2, 3, 0] = Cave().instantiate(playableCharacter)
            it[3, 3, 0] = InnerCave().instantiate(playableCharacter)
            it[3, 4, 0] = Outskirts().instantiate(playableCharacter)
        }
        return regionMaker.make(2, 0, 0)
    }
    internal companion object {
        internal const val NAME = "Everglades"
        private const val DESCRIPTION = "The starting place."
    }
}
