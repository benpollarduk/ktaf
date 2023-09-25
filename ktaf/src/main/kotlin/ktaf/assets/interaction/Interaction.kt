package ktaf.assets.interaction

import ktaf.assets.Item

/**
 * Provides a lambda signature for interactions between an [item] and a [target] to return a [InteractionResult].
 */
public typealias Interaction = (item: Item, target: InteractionTarget) -> InteractionResult
