package com.github.benpollarduk.ktaf.example.everglades.items

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.InteractionEffect
import com.github.benpollarduk.ktaf.assets.interaction.InteractionResult
import com.github.benpollarduk.ktaf.example.global.items.Knife
import com.github.benpollarduk.ktaf.extensions.equalsExaminable
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class ConchShell : AssetTemplate<Item> {
    override fun instantiate(): Item {
        return Item(NAME, DESCRIPTION, true).also {
            it.interaction = { item, _ ->
                when {
                    Knife.NAME.equalsExaminable(item) -> {
                        InteractionResult(
                            InteractionEffect.FATAL_EFFECT,
                            item,
                            "You slash at the conch shell and it shatters into tiny pieces. " +
                                "Without the conch shell you are well and truly in trouble."
                        )
                    } else -> {
                        InteractionResult(
                            InteractionEffect.NO_EFFECT,
                            item
                        )
                    }
                }
            }
        }
    }
    internal companion object {
        internal const val NAME = "Conch Shell"
        private const val DESCRIPTION = "A pretty conch shell, it is about the size of a coconut."
    }
}
