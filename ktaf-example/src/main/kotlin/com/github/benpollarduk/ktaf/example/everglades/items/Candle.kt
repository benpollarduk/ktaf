package com.github.benpollarduk.ktaf.example.everglades.items

import com.github.benpollarduk.ktaf.assets.ConditionalDescription
import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.commands.CustomCommand
import com.github.benpollarduk.ktaf.extensions.toIdentifier
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import com.github.benpollarduk.ktaf.utilities.templates.ItemTemplate

internal class Candle : ItemTemplate() {

    private var isLit: Boolean = true
    override fun instantiate(playableCharacter: PlayableCharacter, room: Room?): Item {
        return Item(
            NAME.toIdentifier(),
            ConditionalDescription(
                DESCRIPTION_LIT,
                DESCRIPTION_EXTINGUISHED
            ) { -> isLit },
            true
        ).also {
            it.commands = listOf(
                CustomCommand(
                    CommandHelp(
                        "Extinguish",
                        "Extinguish the candle."
                    ),
                    true
                ) { _, _ ->
                    isLit = false
                    it.commands = emptyList()
                    Reaction(
                        ReactionResult.OK,
                        "You extinguish the candle."
                    )
                }
            )
        }
    }
    internal companion object {
        internal const val NAME = "Candle"
        private const val DESCRIPTION_LIT = "A small candle, it flickers slowly in the breeze."
        private const val DESCRIPTION_EXTINGUISHED = "A small candle, no longer lit."
    }
}
