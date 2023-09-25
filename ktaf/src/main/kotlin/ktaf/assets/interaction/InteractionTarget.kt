package ktaf.assets.interaction

import ktaf.assets.Item

/**
 * Provides a contract for any object that can be a target for an interaction.
 */
public interface InteractionTarget {
    /**
     * Interact with the specified [item].
     */
    public fun interact(item: Item): InteractionResult
}
