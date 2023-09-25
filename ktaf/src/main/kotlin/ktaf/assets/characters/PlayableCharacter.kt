package ktaf.assets.characters

import ktaf.assets.Description
import ktaf.assets.Identifier
import ktaf.assets.Item
import ktaf.assets.interaction.InteractionEffect
import ktaf.assets.interaction.InteractionResult
import ktaf.assets.interaction.InteractionTarget

/**
 * A playable character with the specified [identifier] and [description] and [items].
 */
public class PlayableCharacter(
    override var identifier: Identifier,
    override var description: Description,
    items: List<Item> = emptyList()
) : Character() {

    init {
        items.forEach { acquireItem(it) }
    }

    /**
     * Trigger this [Player] to use the specified [item] on the specified [target].
     */
    public fun useItem(item: Item, target: InteractionTarget): InteractionResult {
        val result = target.interact(item)

        if (result.effect == InteractionEffect.FATAL_EFFECT) {
            isAlive = false
        }

        return result
    }

    /**
     * Interact with the specified [item] to obtain a [InteractionResult].
     */
    override fun interact(item: Item): InteractionResult {
        return interactWithItem(item)
    }

    /**
     * A playable character with the specified [identifier] and [description] and [items].
     */
    public constructor(
        identifier: String,
        description: String,
        items: List<Item> = emptyList()
    ) : this(Identifier(identifier), Description(description), items)
}
