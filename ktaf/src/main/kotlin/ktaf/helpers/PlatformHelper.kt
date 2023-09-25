package ktaf.helpers

/**
 * Returns a platform specific newline string.
 * For Windows platforms '\r\n' will be used.
 * In all other cases '\n' will be used.
 */
public fun newline(): String {
    val os = System.getProperty("os.name").lowercase()
    return newline(os)
}

/**
 * Returns a platform specific newline string for the given [osName].
 * For Windows platforms '\r\n' will be used.
 * In all other cases '\n' will be used.
 */
public fun newline(osName: String): String {
    return if (osName.contains("win")) {
        "\r\n" // Windows
    } else {
        "\n" // Unix-like
    }
}
