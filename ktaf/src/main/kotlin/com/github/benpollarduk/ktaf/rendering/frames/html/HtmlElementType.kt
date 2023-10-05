package com.github.benpollarduk.ktaf.rendering.frames.html

/**
 * Provides options for HTML element types.
 */
public sealed class HtmlElementType {
    /**
     * Full document with optional [title] and [css].
     */
    public data class Document(public val title: String = "", public val css: String = "") : HtmlElementType()

    /**
     * Div only.
     */
    public object Div : HtmlElementType()
}
