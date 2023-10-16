package com.github.benpollarduk.ktaf.example.hub

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.example.hub.rooms.Clearing
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import com.github.benpollarduk.ktaf.utilities.templates.RegionTemplate

internal class Hub : RegionTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Region {
        val regionMaker = RegionMaker(NAME, DESCRIPTION).also {
            it[0, 0, 0] = Clearing().instantiate(playableCharacter)
        }
        return regionMaker.make(Clearing.NAME)
    }

    internal companion object {
        internal const val NAME = "Jungle"
        private const val DESCRIPTION = "A dense jungle, somewhere tropical."
    }
}
