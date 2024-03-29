package com.github.benpollarduk.ktaf.assets

import com.github.benpollarduk.ktaf.assets.interaction.InteractWithItem
import com.github.benpollarduk.ktaf.assets.interaction.Interaction
import com.github.benpollarduk.ktaf.assets.interaction.InteractionEffect
import com.github.benpollarduk.ktaf.assets.interaction.InteractionResult

/**
 * Provides an item that can appear in a [Game]. The item must have a [identifier] and a [description].
 * Optionally the item can be made takeable by setting [takeable] to true, and an interaction to define how this
 * interacts with various [InteractWithItem] specified with [interaction].
 */
public class Item(
    override var identifier: Identifier,
    override var description: Description,
    public var takeable: Boolean = false,
    public var interaction: Interaction = defaultInteraction
) : ExaminableObject(), InteractWithItem {

    /**
     * Provides an item that can appear in a [Game]. The item must have a [identifier] and a [description].
     * Optionally the item can be made takeable by setting [takeable] to true, and a interaction to define how this
     * interacts with various [InteractWithItem] specified with [interaction].
     */
    public constructor(
        identifier: String,
        description: String,
        takeable: Boolean = false,
        interaction: Interaction = defaultInteraction
    ) : this(Identifier(identifier), Description(description), takeable, interaction)

    /**
     * Returns true if the [Item] can be taken by the [PlayableCharacter].
     */
    public val isTakeable: Boolean
        get() { return takeable }

    /**
     * Morph this [Item] in to the specified [item].
     */
    public fun morph(item: Item) {
        identifier = item.identifier
        description = item.description
        isPlayerVisible = item.isPlayerVisible
        takeable = item.isTakeable
    }

    override fun interact(item: Item): InteractionResult {
        return interaction.invoke(item)
    }

    public companion object {
        /**
         * A default [Interaction] that has no effect.
         */
        public val defaultInteraction: Interaction = { item ->
            InteractionResult(InteractionEffect.NO_EFFECT, item)
        }
    }
}
