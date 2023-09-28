package example.everglades.items

import example.global.items.Knife
import ktaf.assets.Item
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.interaction.InteractionEffect
import ktaf.assets.interaction.InteractionResult
import ktaf.assets.locations.Room
import ktaf.extensions.equalsExaminable
import ktaf.utilities.templates.ItemTemplate

internal class ConchShell : ItemTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter, room: Room): Item {
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
