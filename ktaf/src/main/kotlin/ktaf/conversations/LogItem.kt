package ktaf.conversations

/**
 * A logged element of a [Conversation], consisting of the [participant] and the [line] that they said.
 */
public data class LogItem(public val participant: Participant, public val line: String)
