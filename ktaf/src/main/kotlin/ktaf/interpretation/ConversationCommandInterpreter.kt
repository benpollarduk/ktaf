package ktaf.interpretation

import ktaf.commands.conversation.End
import ktaf.commands.conversation.Next
import ktaf.commands.conversation.Respond
import ktaf.extensions.ensureFinishedSentence
import ktaf.extensions.insensitiveEquals
import ktaf.extensions.toSpeech
import ktaf.extensions.tryParseInt
import ktaf.logic.Game

/**
 * Provides an [Interpreter] for conversations.
 */
public class ConversationCommandInterpreter : Interpreter {
    override val supportedCommands: List<CommandHelp>
        get() = ConversationCommandInterpreter.supportedCommands

    override fun interpret(input: String, game: Game): InterpretationResult {
        if (game.activeConverser == null) {
            return InterpretationResult.fail
        }

        if (input.insensitiveEquals(END)) {
            return InterpretationResult(true, End())
        }

        if (input.trim().isEmpty()) {
            return InterpretationResult(true, Next())
        }

        val conversation = game.activeConverser?.conversation
        val responses = conversation?.currentParagraph?.responses ?: return InterpretationResult.fail
        val index = input.tryParseInt() ?: return InterpretationResult.fail

        if (index > 0 && index <= responses.size) {
            return InterpretationResult(true, Respond(responses[index - 1]))
        }

        return InterpretationResult.fail
    }

    override fun getContextualCommandHelp(game: Game): List<CommandHelp> {
        val converser = game.activeConverser ?: return emptyList()
        val commands = mutableListOf<CommandHelp>()
        val paragraph = converser.conversation.currentParagraph ?: return commands.toList()

        for (i in 0 until paragraph.responses.size) {
            commands.add(
                CommandHelp(
                    (i + 1).toString(),
                    paragraph.responses[i].line.ensureFinishedSentence().toSpeech()
                )
            )
        }

        commands.add(CommandHelp(END, "End the conversation."))

        return commands.toList()
    }

    public companion object {
        /**
         * Get the string for the [End] command.
         */
        public const val END: String = "End"

        /**
         * Get the supported commands.
         */
        public val supportedCommands: List<CommandHelp> = listOf(CommandHelp(END, "End the conversation"))
    }
}
