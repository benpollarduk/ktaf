package example.everglades.rooms

import example.everglades.items.ConchShell
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.utilities.templates.RoomTemplate

internal class GreatWesternOcean : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.EAST)
            )
        ).also {
            it.addItem(ConchShell().instantiate(playableCharacter, it))
        }
    }
    internal companion object {
        internal const val NAME = "Great Western Ocean"
        private const val DESCRIPTION = "The Great Western Ocean stretches to the horizon. The shore runs to the " +
            "north and south. You can hear the lobstosities clicking hungrily. To the east is a small clearing."
    }
}
