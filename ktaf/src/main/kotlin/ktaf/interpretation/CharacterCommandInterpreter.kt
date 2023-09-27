package ktaf.interpretation

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.commands.Command
import ktaf.commands.game.Talk
import ktaf.extensions.insensitiveEquals
import ktaf.logic.Game

/**
 * Provides an [Interpreter] for character commands.
 */
public class CharacterCommandInterpreter : Interpreter {
    private fun tryParseTalk(text: String, game: Game): Command? {
        val verb = CommonInterpretation.extractVerb(text)
        var noun = CommonInterpretation.extractNoun(text)
        return if (!verb.insensitiveEquals(talk) && !verb.insensitiveEquals(talkShort)) {
            null
        } else {
            return when {
                (noun.length > 3 && noun.startsWith("$to ", true)) -> {
                    noun = noun.substring(3)
                    val c: NonPlayableCharacter? = game.overworld.currentRegion?.currentRoom?.findCharacter(noun)
                    Talk(c)
                }
                (game.overworld.currentRegion?.currentRoom?.characters?.size == 1) -> {
                    Talk(game.overworld.currentRegion?.currentRoom?.characters?.firstOrNull())
                }
                else -> {
                    Talk(null)
                }
            }
        }
    }

    override val supportedCommands: List<CommandHelp>
        get() = CharacterCommandInterpreter.supportedCommands

    override fun interpret(input: String, game: Game): InterpretationResult {
        val command: Command? = tryParseTalk(input, game)
        if (command != null) {
            return InterpretationResult(true, command)
        }

        return InterpretationResult.fail
    }

    override fun getContextualCommandHelp(game: Game): List<CommandHelp> {
        if (game.activeConverser?.conversation != null) {
            return emptyList()
        }

        val commands = mutableListOf<CommandHelp>()

        if (game.overworld.currentRegion?.currentRoom?.characters?.any() == true) {
            commands.add(talkCommandHelp)
        }

        return commands.toList()
    }

    public companion object {
        /**
         * Get the string for the [Talk] command.
         */
        public const val talk: String = "Talk"

        /**
         * Get the string for the short [Talk] command.
         */
        public const val talkShort: String = "L"

        /**
         * Get the string for the to command.
         */
        public const val to: String = "To"

        /**
         * Get the string for the variable command.
         */
        public const val variable: String = "__"

        private val talkCommandHelp = CommandHelp(
            "$talk / $talkShort ${to.lowercase()} $variable",
            "Talk to a character"
        )

        /**
         * Get the supported commands.
         */
        public val supportedCommands: List<CommandHelp> = listOf(
            talkCommandHelp
        )
    }
}
