package ktaf.interpretation

import ktaf.assets.locations.Direction
import java.util.*

public object CommonInterpretation {
    /**
     * Try and parse a [String] specified by [text] to a [Direction]. If the [text] cannot be parsed null is returned.
     */
    internal fun tryParseDirection(text: String): Direction? {
        return when (text.lowercase()) {
            MovementCommandInterpreter.north.lowercase() -> Direction.NORTH
            MovementCommandInterpreter.northShort.lowercase() -> Direction.NORTH
            MovementCommandInterpreter.east.lowercase() -> Direction.EAST
            MovementCommandInterpreter.eastShort.lowercase() -> Direction.EAST
            MovementCommandInterpreter.south.lowercase() -> Direction.SOUTH
            MovementCommandInterpreter.southShort.lowercase() -> Direction.SOUTH
            MovementCommandInterpreter.west.lowercase() -> Direction.WEST
            MovementCommandInterpreter.westShort.lowercase() -> Direction.WEST
            MovementCommandInterpreter.up.lowercase() -> Direction.UP
            MovementCommandInterpreter.upShort.lowercase() -> Direction.UP
            MovementCommandInterpreter.down.lowercase() -> Direction.DOWN
            MovementCommandInterpreter.downShort.lowercase() -> Direction.DOWN
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
