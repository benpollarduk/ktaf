package example.global.player

import example.global.items.Knife
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.interaction.InteractionEffect
import ktaf.assets.interaction.InteractionResult
import ktaf.extensions.equalsExaminable
import ktaf.utilities.templates.PlayableCharacterTemplate

internal class Player : PlayableCharacterTemplate() {
    override fun instantiate(): PlayableCharacter {
        return PlayableCharacter(NAME, DESCRIPTION, listOf(Knife().instantiate())).also {
            it.interaction = { item, _ ->
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
