package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.ConditionalDescription
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.example.everglades.items.Candle
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class Tunnel : AssetTemplate<Room> {
    override fun instantiate(): Room {
        return Room(
            NAME,
            DESCRIPTION_WITH_CANDLE,
            listOf(
                Exit(Direction.UP)
            )
        ).also {
            it.addItem(Candle().instantiate())
            it.specifyConditionalDescription(
                ConditionalDescription(
                    DESCRIPTION_WITH_CANDLE,
                    DESCRIPTION_NO_CANDLE
                ) { -> it.containsItem(Candle.NAME) }
            )
        }
    }
    internal companion object {
        internal const val NAME = "Tunnel"
        private const val DESCRIPTION_WITH_CANDLE = "The crack led to the entrance to a rough tunnel. The light " +
            "you could see earlier was coming from a candle sat upon a rock, flickering away slowly."
        private const val DESCRIPTION_NO_CANDLE = "The crack led to the entrance to a rough tunnel."
    }
}
