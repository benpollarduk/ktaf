package com.github.benpollarduk.ktaf.example.hub.rooms

import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.example.hub.npc.Parrot
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class Clearing : AssetTemplate<Room> {
    override fun instantiate(): Room {
        val room = Room(NAME, DESCRIPTION).also {
            it.addCharacter(Parrot().instantiate())
        }

        return room
    }

    internal companion object {
        internal const val NAME = "Jungle Clearing"
        private const val DESCRIPTION = "You are in a small clearing in a jungle, tightly enclosed by undergrowth. " +
            "You have no idea how you got here. The chirps and buzzes coming from insects in the undergrowth are " +
            "intense. There are some stone pedestals in front of you. Each has a small globe on top of it."
    }
}
