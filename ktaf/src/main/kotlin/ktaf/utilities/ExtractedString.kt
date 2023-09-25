package ktaf.utilities

/**
 * Provides information about an extracted string, including the [extractedWord] and the [remainder] of the original
 * string.
 */
internal data class ExtractedString(
    internal val extractedWord: String,
    internal val rest: String
)
