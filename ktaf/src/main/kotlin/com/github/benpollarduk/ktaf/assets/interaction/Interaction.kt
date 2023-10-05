package com.github.benpollarduk.ktaf.assets.interaction

import com.github.benpollarduk.ktaf.assets.Item

/**
 * Provides a lambda signature for interactions between an [item] and a [target] to return a [InteractionResult].
 */
public typealias Interaction = (item: Item, target: InteractionTarget) -> InteractionResult
