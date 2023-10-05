package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.assets.locations.Direction

public object CommonInterpretation {
    /**
     * Try and parse a [String] specified by [text] to a [Direction]. If the [text] cannot be parsed null is returned.
     */
    internal fun tryParseDirection(text: String): Direction? {
        return when (text.lowercase()) {
            MovementCommandInterpreter.NORTH.lowercase() -> Direction.NORTH
            MovementCommandInterpreter.NORTH_SHORT.lowercase() -> Direction.NORTH
            MovementCommandInterpreter.EAST.lowercase() -> Direction.EAST
            MovementCommandInterpreter.EAST_SHORT.lowercase() -> Direction.EAST
            MovementCommandInterpreter.SOUTH.lowercase() -> Direction.SOUTH
            MovementCommandInterpreter.SOUTH_SHORT.lowercase() -> Direction.SOUTH
            MovementCommandInterpreter.WEST.lowercase() -> Direction.WEST
            MovementCommandInterpreter.WEST_SHORT.lowercase() -> Direction.WEST
            MovementCommandInterpreter.UP.lowercase() -> Direction.UP
            MovementCommandInterpreter.UP_SHORT.lowercase() -> Direction.UP
            MovementCommandInterpreter.DOWN.lowercase() -> Direction.DOWN
            MovementCommandInterpreter.DOWN_SHORT.lowercase() -> Direction.DOWN
            else -> null
        }
    }

    /**
     * Extract a verb from the [text].
     */
    internal fun extractVerb(text: String): String {
        val space = ' '
        return if (text.contains(space)) {
            text.substring(0, text.indexOf(space)).trim()
        } else {
            text
        }
    }

    /**
     * Extract a noun from the [text].
     */
    internal fun extractNoun(text: String): String {
        val space = ' '
        return if (text.contains(space)) {
            text.substring(text.indexOf(space)).trim()
        } else {
            ""
        }
    }
}
