package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.example.everglades.items.ConchShell
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class GreatWesternOcean : AssetTemplate<Room> {
    override fun instantiate(): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.EAST)
            )
        ).also {
            it.addItem(ConchShell().instantiate())
        }
    }
    internal companion object {
        internal const val NAME = "Great Western Ocean"
        private const val DESCRIPTION = "The Great Western Ocean stretches to the horizon. The shore runs to the " +
            "north and south. You can hear the lobstosities clicking hungrily. To the east is a small clearing."
    }
}
