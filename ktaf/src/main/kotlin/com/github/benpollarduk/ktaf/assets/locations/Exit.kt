package com.github.benpollarduk.ktaf.assets.locations

import com.github.benpollarduk.ktaf.assets.ConditionalDescription
import com.github.benpollarduk.ktaf.assets.Description
import com.github.benpollarduk.ktaf.assets.ExaminableObject
import com.github.benpollarduk.ktaf.assets.Identifier
import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.InteractWithItem
import com.github.benpollarduk.ktaf.assets.interaction.Interaction
import com.github.benpollarduk.ktaf.assets.interaction.InteractionEffect
import com.github.benpollarduk.ktaf.assets.interaction.InteractionResult

public class Exit(
    public val direction: Direction,
    private var locked: Boolean = false,
    identifier: Identifier? = null,
    description: Description? = null
) : ExaminableObject(), InteractWithItem {
    init {
        this.identifier = identifier ?: Identifier(direction.name)
        this.description = description ?: ConditionalDescription(
            "The exit ${direction.toString().lowercase()} is locked.",
            "The exit ${direction.toString().lowercase()} is unlocked."
        ) { this.isLocked }
    }

    /**
     * Specifies how this [Exit] interacts with various [Item].
     */
    public var interaction: Interaction = { item ->
        InteractionResult(InteractionEffect.NO_EFFECT, item)
    }

    /**
     * Get if this [Exit] is locked.
     */
    public val isLocked: Boolean
        get() = locked

    /**
     * Unlock this [Exit].
     */
    public fun unlock() {
        locked = false
    }

    /**
     * Lock this [Exit].
     */
    public fun lock() {
        locked = true
    }

    /**
     * Interact with the specified [item] to obtain a [InteractionResult].
     */
    override fun interact(item: Item): InteractionResult {
        return interaction.invoke(item)
    }
}
