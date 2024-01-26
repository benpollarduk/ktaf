package com.github.benpollarduk.ktaf.assets.interaction

import com.github.benpollarduk.ktaf.assets.Item

/**
 * Provides a contract for any object that can be a target for an interaction with an [Item].
 */
public fun interface InteractWithItem {
    /**
     * Interact with the specified [item].
     */
    public fun interact(item: Item): InteractionResult
}
