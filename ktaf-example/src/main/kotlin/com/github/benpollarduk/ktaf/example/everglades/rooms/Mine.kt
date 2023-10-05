package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.utilities.templates.RoomTemplate

internal class Mine : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.UP),
            ),
        )
    }
    internal companion object {
        internal const val NAME = "Mine"
        private const val DESCRIPTION = "The crack led to the entrance to an abandoned mine. The light you could " +
            "see earlier was coming from a candle sat upon a rock, flickering away slowly. Next to the candle" +
            "sits a dwarf."
    }
}
