package ktaf.conversations

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.extensions.ensureFinishedSentence
import ktaf.extensions.toSpeech
import ktaf.logic.Game

/**
 * Provides a mechanism for holding a conversation with a [Converser]. The [paragraphs] specify the body of
 * the conversation.
 */
public class Conversation(
    private val paragraphs: List<Paragraph> = emptyList()
) {
    private var paragraphIndex: Int = 0
    private var selectedResponseDelta: Int? = null
    private val mutableLog: MutableList<LogItem> = mutableListOf()

    /**
     * Returns the current [Paragraph].
     */
    public var currentParagraph: Paragraph? = null
        private set

    /**
     * Provides a log of the [Conversation] as an array of [LogItem].
     */
    public val log: List<LogItem>
        get() = mutableLog.toList()

    /**
     * Shift this [Conversation] to a new [Paragraph]. The [delta] specifies the next [Paragraph], which is retrieved
     * from the [paragraphs] array. If the [delta] shifts the conversation out of bounds then null is returned.
     */
    private fun shift(delta: Int): Paragraph? {
        paragraphIndex += delta

        return if (paragraphIndex >= 0 && paragraphIndex < paragraphs.count()) {
            paragraphs[paragraphIndex]
        } else {
            currentParagraph
        }
    }

    /**
     * Trigger the next line in this [Conversation] to obtain a [Reaction].
     */
    public fun next(game: Game): Reaction {
        if (paragraphs.isEmpty()) {
            return Reaction(ReactionResult.INTERNAL, "No paragraphs.")
        }

        val current = currentParagraph

        when {
            current == null -> {
                currentParagraph = paragraphs.firstOrNull()
            }
            current.canRespond -> {
                selectedResponseDelta?.let { delta ->
                    currentParagraph = shift(delta)
                    selectedResponseDelta = null
                } ?: return Reaction(ReactionResult.INTERNAL, "Awaiting response.")
            }
            else -> {
                currentParagraph = shift(current.delta)
            }
        }

        if (currentParagraph == current) {
            return endOfConversation
        }

        currentParagraph?.let {
            it.action.invoke(game)
            val line = it.line.toSpeech()
            mutableLog.add(LogItem(Participant.OTHER, line))
            return Reaction(ReactionResult.INTERNAL, line)
        } ?: return endOfConversation
    }

    public fun respond(response: Response, game: Game): Reaction {
        currentParagraph?.let {
            if (!it.responses.contains(response)) {
                return@respond Reaction(ReactionResult.ERROR, "Invalid response.")
            }
            mutableLog.add(LogItem(Participant.PLAYER, response.line.ensureFinishedSentence()))
            selectedResponseDelta = response.delta
            return@respond next(game)
        } ?: return Reaction(ReactionResult.ERROR, "No paragraph.")
    }

    public companion object {
        /**
         * An empty conversation.
         */
        public val empty: Conversation = Conversation(listOf(Paragraph("Nothing to say.")))

        /**
         * The default reaction at the end of a conversation.
         */
        private val endOfConversation = Reaction(ReactionResult.INTERNAL, "End of conversation.")
    }
}
