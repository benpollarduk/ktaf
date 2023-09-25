package ktaf.assets.interaction

/**
 * Provides a reaction consisting of both a [result] and a [description].
 */
public data class Reaction(
    public val result: ReactionResult,
    public val description: String
)
