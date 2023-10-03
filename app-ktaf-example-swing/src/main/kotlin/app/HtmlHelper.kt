package app

/**
 * Provides a helper for HTML operations.
 */
internal object HtmlHelper {
    /**
     * Create default CSS with a specified [bodyWidth].
     */
    internal fun createCSS(bodyWidth: Int = 60): String {
        return """
           body {
                  background-color: black;
                  font-size: 10px;
                  font-family: Consolas, monospace;
                  color: white;
                  word-wrap: break-word;
                  margin: 10px;
                  width:100%;
                  max-width: ${bodyWidth}px;
                }
        """.trimIndent()
    }

    /**
     * Create basic HTML with a simple [message]. The [message] may be HTML.
     */
    internal fun simpleMessage(message: String): String {
        return """
            <html>
                <style>
                    ${createCSS()}
                </style
                <body>
                    $message
                </body>
            </html>
        """.trimIndent()
    }
}
