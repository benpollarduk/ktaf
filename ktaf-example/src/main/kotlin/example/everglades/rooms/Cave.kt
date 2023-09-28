package example.everglades.rooms

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.utilities.templates.RoomTemplate

internal class Cave : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.SOUTH),
                Exit(Direction.EAST)
            )
        )
    }
    internal companion object {
        internal const val NAME = "Cave"
        private const val DESCRIPTION = "The cave is so dark you struggling to see. " +
            "A screeching noise is audible to the east."
    }
}
