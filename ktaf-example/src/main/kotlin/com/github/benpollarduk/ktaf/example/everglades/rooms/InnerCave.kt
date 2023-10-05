package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.ConditionalDescription
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.interaction.InteractionEffect
import com.github.benpollarduk.ktaf.assets.interaction.InteractionResult
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.example.everglades.items.ConchShell
import com.github.benpollarduk.ktaf.example.global.items.Knife
import com.github.benpollarduk.ktaf.extensions.equalsExaminable
import com.github.benpollarduk.ktaf.utilities.templates.RoomTemplate

internal class InnerCave : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(
            NAME,
            "",
            listOf(
                Exit(Direction.WEST),
                Exit(Direction.NORTH, true)
            )
        ).also {
            it.interaction = { item, _ ->
                when {
                    ConchShell.NAME.equalsExaminable(item) -> {
                        it[Direction.NORTH]?.unlock()
                        InteractionResult(
                            InteractionEffect.ITEM_USED_UP,
                            item,
                            "You blow into the Conch Shell. The Conch Shell howls, the " +
                                "bats leave! Conch shell crumbles to pieces."
                        )
                    }
                    Knife.NAME.equalsExaminable(item) -> {
                        InteractionResult(
                            InteractionEffect.NO_EFFECT,
                            item,
                            "You slash wildly at the bats, but there are too many. Don't " +
                                "aggravate them!"
                        )
                    }
                    else -> {
                        InteractionResult(
                            InteractionEffect.NO_EFFECT,
                            item
                        )
                    }
                }
            }

            it.specifyConditionalDescription(
                com.github.benpollarduk.ktaf.assets.ConditionalDescription(
                    "With the bats gone there is daylight to the north. To the west is the cave entrance.",
                    "As you enter the inner cave the screeching gets louder, and in the gloom you can " +
                        "make out what looks like a million sets of eyes looking back at you. Bats! You can just make out a few rays of light coming from the north, but the bats are blocking your way."
                ) { -> it[Direction.NORTH]?.isLocked == false }
            )
        }
    }
    internal companion object {
        internal const val NAME = "Inner Cave"
    }
}
