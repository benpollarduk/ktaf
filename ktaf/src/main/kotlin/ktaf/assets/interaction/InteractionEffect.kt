package ktaf.assets.interaction

/**
 * Enumeration of possible effects to an interaction.
 */
public enum class InteractionEffect {
    /**
     * No effect to the interaction on either the [Item] or the [InteractionTarget].
     */
    NO_EFFECT,

    /**
     * [Item] was used up.
     */
    ITEM_USED_UP,

    /**
     * [Item] morphed into another [Item].
     */
    ITEM_MORPHED,

    /**
     * A fatal effect to the interaction.
     */
    FATAL_EFFECT,

    /**
     * The [InteractionTarget] was used up.
     */
    TARGET_USED_UP,

    /**
     * Any other self-contained effect.
     */
    SELF_CONTAINED
}
