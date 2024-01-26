package com.github.benpollarduk.ktaf.assets.interaction

import com.github.benpollarduk.ktaf.assets.Item

/**
 * Provides a lambda signature for interactions with an [item] to return a [InteractionResult].
 */
public typealias Interaction = (item: Item) -> InteractionResult
