package ktaf.conversations

/**
 * A paragraph within a [Conversation]. Must contain a [line], but can also have an optional [action] that is invoked
 * as a response to this paragraph being triggered. The [delta] is a relative pointer to the next element within the
 * [Conversation].
 */
public class Paragraph(
    public val line: String,
    public val delta: Int = 1,
    public val action: ConversationAction = { }
) {
    /**
     * The available responses to the [line].
     */
    public var responses: List<Response> = emptyList()

    /**
     * Determines if there are [responses] available for this [Paragraph].
     */
    public val canRespond: Boolean
        get() = responses.any()
}
