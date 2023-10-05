package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.utilities.templates.RoomTemplate

internal class Outskirts : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(NAME, DESCRIPTION, listOf(Exit(Direction.SOUTH)))
    }
    internal companion object {
        internal const val NAME = "Outskirts"
        private const val DESCRIPTION = "A vast chasm falls away before you."
    }
}
