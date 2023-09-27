package example.hub.rooms

import example.hub.npc.Parrot
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import ktaf.utilities.templates.RoomTemplate

internal class Clearing : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        val room = Room(NAME, DESCRIPTION).also {
            it.addCharacter(Parrot().instantiate(playableCharacter, it))
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
