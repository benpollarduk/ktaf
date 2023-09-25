package ktaf.conversations

/**
 * A response which forms part of a [Conversation]. The [line] forms the body of the response and the [delta] is a
 * relative pointer to the next element within the [Conversation].
 */
public data class Response(public val line: String, public val delta: Int = 1)
