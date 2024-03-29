package com.github.benpollarduk.ktaf.example.global.player

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.interaction.InteractionEffect
import com.github.benpollarduk.ktaf.assets.interaction.InteractionResult
import com.github.benpollarduk.ktaf.example.global.items.Knife
import com.github.benpollarduk.ktaf.extensions.equalsExaminable
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class Player : AssetTemplate<PlayableCharacter> {
    override fun instantiate(): PlayableCharacter {
        return PlayableCharacter(NAME, DESCRIPTION).also {
            it.attributes.add("£", 5)
            it.acquireItem(Knife().instantiate())
            it.interaction = { item ->
                when {
                    Knife.NAME.equalsExaminable(item) -> {
                        InteractionResult(
                            InteractionEffect.FATAL_EFFECT,
                            item,
                            "You slash wildly at your own throat. You are dead."
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
        internal const val NAME = "Ben"
        private const val DESCRIPTION = "You are a 25 year old man, dressed in shorts, a t-shirt and flip-flops."
    }
}
