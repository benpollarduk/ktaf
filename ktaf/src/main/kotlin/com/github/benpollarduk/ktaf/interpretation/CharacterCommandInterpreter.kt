package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.commands.game.Talk
import com.github.benpollarduk.ktaf.extensions.insensitiveEquals
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides an [Interpreter] for character commands.
 */
public class CharacterCommandInterpreter : Interpreter {
    private fun tryParseTalk(text: String, game: Game): Command? {
        val verb = CommonInterpretation.extractVerb(text)
        var noun = CommonInterpretation.extractNoun(text)
        return if (!verb.insensitiveEquals(TALK) && !verb.insensitiveEquals(TALK_SHORT)) {
            null
        } else {
            return when {
                (noun.length > 3 && noun.startsWith("$TO ", true)) -> {
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

        if (game.player.canConverse && game.overworld.currentRegion?.currentRoom?.characters?.any() == true) {
            commands.add(talkCommandHelp)
        }

        return commands.toList()
    }

    public companion object {
        /**
         * Get the string for the [Talk] command.
         */
        public const val TALK: String = "Talk"

        /**
         * Get the string for the short [Talk] command.
         */
        public const val TALK_SHORT: String = "L"

        /**
         * Get the string for the to command.
         */
        public const val TO: String = "To"

        /**
         * Get the string for the variable command.
         */
        public const val VARIABLE: String = "__"

        private val talkCommandHelp = CommandHelp(
            "$TALK / $TALK_SHORT ${TO.lowercase()} $VARIABLE",
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
