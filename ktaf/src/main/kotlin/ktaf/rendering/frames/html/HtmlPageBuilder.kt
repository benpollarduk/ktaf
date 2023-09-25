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
}
