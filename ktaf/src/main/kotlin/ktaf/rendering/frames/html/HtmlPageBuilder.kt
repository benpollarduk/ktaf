package ktaf.rendering.frames.html

import java.lang.StringBuilder

/**
 * Provides functionality for building HTML pages. Specify the build mode with the [mode] property.
 */
public class HtmlPageBuilder(
    public val mode: HtmlElementType = HtmlElementType.Document()
) {
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
    public fun p(value: String) {
        append("p", value)
    }

    /**
     * Append bold.
     */
    public fun b(value: String) {
        append("b", value)
    }

    /**
     * Append italic.
     */
    public fun i(value: String) {
        append("i", value)
    }

    /**
     * Append underline.
     */
    public fun u(value: String) {
        append("u", value)
    }

    /**
     * Append.
     */
    public fun append(value: String) {
        builder.append(value)
    }

    /**
     * Append a break.
     */
    public fun br() {
        builder.append("<br>")
    }

    /**
     * Append pre-formatted.
     */
    public fun pre(value: String) {
        append("pre", value)
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
