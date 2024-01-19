package com.github.benpollarduk.ktaf.example.everglades

import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.example.everglades.rooms.Cave
import com.github.benpollarduk.ktaf.example.everglades.rooms.CaveMouth
import com.github.benpollarduk.ktaf.example.everglades.rooms.ForestEntrance
import com.github.benpollarduk.ktaf.example.everglades.rooms.ForestFloor
import com.github.benpollarduk.ktaf.example.everglades.rooms.GreatWesternOcean
import com.github.benpollarduk.ktaf.example.everglades.rooms.InnerCave
import com.github.benpollarduk.ktaf.example.everglades.rooms.Outskirts
import com.github.benpollarduk.ktaf.example.everglades.rooms.TreeHouse
import com.github.benpollarduk.ktaf.example.everglades.rooms.Tunnel
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class Everglades : AssetTemplate<Region> {
    override fun instantiate(): Region {
        val regionMaker = RegionMaker(NAME, DESCRIPTION).also {
            it[2, 0, 0] = ForestEntrance().instantiate()
            it[2, 0, 1] = TreeHouse().instantiate()
            it[2, 1, 0] = ForestFloor().instantiate()
            it[2, 2, 0] = CaveMouth().instantiate()
            it[1, 2, 0] = GreatWesternOcean().instantiate()
            it[2, 3, 0] = Cave().instantiate()
            it[2, 3, -1] = Tunnel().instantiate()
            it[3, 3, 0] = InnerCave().instantiate()
            it[3, 4, 0] = Outskirts().instantiate()
        }
        return regionMaker.make(ForestEntrance.NAME)
    }
    internal companion object {
        internal const val NAME = "Everglades"
        private const val DESCRIPTION = "The starting place."
    }
}
