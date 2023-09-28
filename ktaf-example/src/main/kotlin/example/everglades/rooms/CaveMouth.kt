package example.everglades.rooms

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.utilities.templates.RoomTemplate

internal class CaveMouth : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.NORTH),
                Exit(Direction.SOUTH),
                Exit(Direction.WEST)
            )
        )
    }
    internal companion object {
        internal const val NAME = "Cave Mouth"
        private const val DESCRIPTION = "A cave mouth looms in front of you to the north. You can hear the sound " +
            "of the ocean coming from the west."
    }
}
