package com.github.benpollarduk.ktaf.example.everglades

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.example.everglades.rooms.Cave
import com.github.benpollarduk.ktaf.example.everglades.rooms.CaveMouth
import com.github.benpollarduk.ktaf.example.everglades.rooms.ForestEntrance
import com.github.benpollarduk.ktaf.example.everglades.rooms.ForestFloor
import com.github.benpollarduk.ktaf.example.everglades.rooms.GreatWesternOcean
import com.github.benpollarduk.ktaf.example.everglades.rooms.InnerCave
import com.github.benpollarduk.ktaf.example.everglades.rooms.Mine
import com.github.benpollarduk.ktaf.example.everglades.rooms.Outskirts
import com.github.benpollarduk.ktaf.example.everglades.rooms.TreeHouse
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import com.github.benpollarduk.ktaf.utilities.templates.RegionTemplate

internal class Everglades : RegionTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Region {
        val regionMaker = RegionMaker(NAME, DESCRIPTION).also {
            it[2, 0, 0] = ForestEntrance().instantiate(playableCharacter)
            it[2, 0, 1] = TreeHouse().instantiate(playableCharacter)
            it[2, 1, 0] = ForestFloor().instantiate(playableCharacter)
            it[2, 2, 0] = CaveMouth().instantiate(playableCharacter)
            it[1, 2, 0] = GreatWesternOcean().instantiate(playableCharacter)
            it[2, 3, 0] = Cave().instantiate(playableCharacter)
            it[2, 3, -1] = Mine().instantiate(playableCharacter)
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
