package com.github.benpollarduk.ktaf.assets.characters

import com.github.benpollarduk.ktaf.assets.ExaminableObject
import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.Interaction
import com.github.benpollarduk.ktaf.assets.interaction.InteractionEffect
import com.github.benpollarduk.ktaf.assets.interaction.InteractionResult
import com.github.benpollarduk.ktaf.assets.interaction.InteractionTarget

/**
 * Provides a base for all Characters.
 */
public abstract class Character : com.github.benpollarduk.ktaf.assets.ExaminableObject(), InteractionTarget {

    /**
     * Specifies how this [Character] interacts with various [Item].
     */
    public var interaction: Interaction = { item, _ ->
        InteractionResult(InteractionEffect.NO_EFFECT, item)
    }

    /**
     * Determines if this [Character] is alive.
     */
    public var isAlive: Boolean = true
        protected set

    /**
     * This characters [Item].
     */
    public val items: List<Item>
        get() { return itemsList.toList() }

    private val itemsList: MutableList<Item> = mutableListOf()

    protected fun interactWithItem(item: Item): InteractionResult {
        return interaction.invoke(item, this)
    }

    /**
     * Kill this [Character].
     */
    public fun kill() {
        isAlive = false
    }

    /**
     * Acquire the specified [item].
     */
    public fun acquireItem(item: Item) {
        itemsList.add(item)
    }

    /**
     * Dequire the specified [item].
     */
    public fun decquireItem(item: Item) {
        itemsList.remove(item)
    }

    /**
     * Determine if this [Character] has the specified [item]. When [includeInvisibleItems] is set to true even [Item]
     * that have been marked as invisible to the player will be inspected.
     */
    public fun hasItem(item: Item, includeInvisibleItems: Boolean = false): Boolean {
        return itemsList.contains(item) && (includeInvisibleItems || item.isPlayerVisible)
    }

    /**
     * Find an return an item from the specified [itemName]. When [includeInvisibleItems] is set to true even [Item]
     * that have been marked as invisible to the player will be inspected. If the [Character] does not have the [Item]
     * then null wil be returned.
     */
    public fun findItem(itemName: String, includeInvisibleItems: Boolean = false): Item? {
        return itemsList.firstOrNull {
            it.identifier.equals(itemName) && (includeInvisibleItems || it.isPlayerVisible)
        }
    }

    /**
     * Give a specified [item] to a specified [character]. This will return true if the transaction was successful,
     * else false.
     */
    public fun give(item: Item, character: Character): Boolean {
        if (!hasItem(item)) {
            return false
        }

        decquireItem(item)
        character.acquireItem(item)
        return true
    }
}
