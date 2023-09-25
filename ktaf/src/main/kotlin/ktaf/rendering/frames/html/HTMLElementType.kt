package ktaf.rendering.frames.html

/**
 * Provides options for HTML element types.
 */
public sealed class HTMLElementType {
    /**
     * Full document with optional [title] and [css].
     */
    public data class Document(public val tile: String = "", public val css: String = "") : HTMLElementType()

    /**
     * Div only.
     */
    public object Div : HTMLElementType()
}
