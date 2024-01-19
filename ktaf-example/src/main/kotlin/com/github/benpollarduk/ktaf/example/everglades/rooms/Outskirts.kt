package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class Outskirts : AssetTemplate<Room> {
    override fun instantiate(): Room {
        return Room(NAME, DESCRIPTION, listOf(Exit(Direction.SOUTH)))
    }
    internal companion object {
        internal const val NAME = "Outskirts"
        private const val DESCRIPTION = "A vast chasm falls away before you."
    }
}
