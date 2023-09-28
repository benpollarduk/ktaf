package example.everglades.rooms

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.utilities.templates.RoomTemplate

internal class ForestEntrance : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.NORTH)
            )
        )
    }
    internal companion object {
        internal const val NAME = "Forest Entrance"
        private const val DESCRIPTION = "You are standing on the edge of a beautiful forest. There is a parting " +
            "in the trees to the north."
    }
}
