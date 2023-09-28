package example.everglades.rooms

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.utilities.templates.RoomTemplate

internal class Outskirts : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(NAME, DESCRIPTION, listOf(Exit(Direction.SOUTH)))
    }
    internal companion object {
        internal const val NAME = "Outskirts"
        private const val DESCRIPTION = "A vast chasm falls away before you."
    }
}
