package example.everglades.rooms

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.utilities.templates.RoomTemplate

internal class ForestFloor : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.NORTH),
                Exit(Direction.SOUTH)
            )
        )
    }
    internal companion object {
        internal const val NAME = "Forest Floor"
        private const val DESCRIPTION = "The forest is dense, with a few patches of light breaking the darkness. " +
            "To the north is what looks like a small cave, to the south is the entrance to the forest."
    }
}
