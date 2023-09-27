package ktaf.rendering.frames.ansi

/**
 * Enumeration of ANSI color codes.
 */
public enum class AnsiColor(public val code: Int) {
    /**
     * Reset (0).
     */
    RESET(0),

    /**
     * Black (30).
     */
    BLACK(30),

    /**
     * Red (31).
     */
    RED(31),

    /**
     * Green (32).
     */
    GREEN(32),

    /**
     * Yellow (33).
     */
    YELLOW(33),

    /**
     * Blue (34).
     */
    BLUE(34),

    /**
     * Magenta (35).
     */
    MAGENTA(35),

    /**
     * Cyan (36).
     */
    CYAN(36),

    /**
     * White (37).
     */
    WHITE(37),

    /**
     * Bright black (90).
     */
    BRIGHT_BLACK(90),

    /**
     * Bright red (91).
     */
    BRIGHT_RED(91),

    /**
     * Bright green (92).
     */
    BRIGHT_GREEN(92),

    /**
     * Bright yellow (93).
     */
    BRIGHT_YELLOW(93),

    /**
     * Bright blue (94).
     */
    BRIGHT_BLUE(94),

    /**
     * Bright magenta (95).
     */
    BRIGHT_MAGENTA(95),

    /**
     * Bright cyan (96).
     */
    BRIGHT_CYAN(96),

    /**
     * Bright white (97).
     */
    BRIGHT_WHITE(97)
    ;

    /**
     * Convert to font color escape code.
     */
    public fun toFontColorEscapeCode(): String {
        return "\u001B[${this.code}m"
    }

    /**
     * Convert to background color escape code.
     */
    public fun toBackgroundColorEscapeCode(): String {
        return "\u001B[${this.code + 10}m"
    }
}
