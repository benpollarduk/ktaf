package ktaf.rendering.frames.html

import java.lang.StringBuilder

/**
 * Provides functionality for building HTML pages.
 */
public class HtmlPageBuilder {
    private val builder = StringBuilder()
    override fun toString(): String {
        return builder.toString()
    }

    /**
     * Append a header 1.
     */
    public fun h1(value: String) {
        append("h1", value)
    }

    /**
     * Append a header 2.
     */
    public fun h2(value: String) {
        append("h2", value)
    }

    /**
     * Append a header 3.
     */
    public fun h3(value: String) {
        append("h3", value)
    }

    /**
     * Append a header 4.
     */
    public fun h4(value: String) {
        append("h4", value)
    }

    /**
     * Append a paragraph.
     */
    public fun p(value: String, bold: Boolean = false, italic: Boolean = false) {
        val tags = mutableListOf("p")

        if (bold) {
            tags.add("b")
        }

        if (italic) {
            tags.add("i")
        }

        append(tags, value)
    }

    /**
     * Append a break.
     */
    public fun br() {
        builder.append("<br>")
    }

    private fun append(tag: String, value: String) {
        val tags = listOf(tag)
        append(tags, value)
    }

    private fun append(tags: List<String>, value: String) {
        for (i in tags.indices) {
            builder.append("<${tags[i]}>")
        }

        builder.append(value)

        for (i in tags.size - 1 downTo 0) {
            builder.append("</${tags[i]}>")
        }
    }
}
