package com.github.benpollarduk.ktaf.conversations

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.conversations.instructions.EndOfParagraphInstruction
import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.extensions.toSpeech
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a mechanism for holding a conversation with a [Converser]. The [paragraphs] specify the body of
 * the conversation.
 */
public class Conversation(
    private val paragraphs: List<Paragraph> = emptyList()
) {
    private var selectedResponseInstruction: EndOfParagraphInstruction? = null
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
     * Shift this [Conversation] to a new [Paragraph]. The [index] specifies the next [Paragraph], which is retrieved
     * from the [paragraphs] array. If the [index] shifts the conversation out of bounds then the current paragraph is
     * returned.
     */
    private fun shift(index: Int): Paragraph? {
        return if (index >= 0 && index < paragraphs.count()) {
            paragraphs[index]
        } else {
            currentParagraph
        }
    }

    /**
     * Update the [currentParagraph] based on an [instruction].
     */
    private fun updateCurrentParagraph(instruction: EndOfParagraphInstruction) {
        val current = currentParagraph ?: return
        currentParagraph = shift(instruction.getIndexOfNext(current, paragraphs))
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
                selectedResponseInstruction?.let {
                    updateCurrentParagraph(it)
                    selectedResponseInstruction = null
                } ?: return Reaction(ReactionResult.INTERNAL, "Awaiting response.")
            }
            else -> {
                updateCurrentParagraph(current.instruction)
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
            selectedResponseInstruction = response.instruction
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
