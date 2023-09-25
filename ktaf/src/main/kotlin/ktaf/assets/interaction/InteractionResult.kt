package ktaf.assets.interaction

import ktaf.assets.Item

/**
 * Describes the result of an interaction, detailing the [effect] that the interaction between the [item] and the
 * target had. If [descriptionOfInteraction] is null the [description] property will return a generated
 * description, otherwise the [descriptionOfInteraction] will be returned.
 */
public class InteractionResult(
    public val effect: InteractionEffect,
    public val item: Item,
    public val descriptionOfInteraction: String?
) : Result {

    override val description: String
        get() {
            if (descriptionOfInteraction != null) {
                return descriptionOfInteraction
            }

            return when (effect) {
                InteractionEffect.NO_EFFECT -> "There was no effect."
                InteractionEffect.ITEM_USED_UP -> "The item was used up."
                InteractionEffect.ITEM_MORPHED -> "The item morphed."
                InteractionEffect.FATAL_EFFECT -> "There was a fatal effect."
                InteractionEffect.TARGET_USED_UP -> "The target was used up."
                InteractionEffect.SELF_CONTAINED -> "The effect was self contained."
            }
        }

    /**
     * Describes the result of an interaction, detailing the [effect] that the interaction between the [item] and the
     * target had.
     */
    public constructor(effect: InteractionEffect, item: Item) : this(effect, item, null)
}
