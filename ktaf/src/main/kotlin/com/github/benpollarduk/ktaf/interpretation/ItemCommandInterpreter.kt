package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.assets.Examinable
import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.InteractionTarget
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.commands.game.Drop
import com.github.benpollarduk.ktaf.commands.game.Examine
import com.github.benpollarduk.ktaf.commands.game.Take
import com.github.benpollarduk.ktaf.commands.game.TakeAll
import com.github.benpollarduk.ktaf.commands.game.Unactionable
import com.github.benpollarduk.ktaf.commands.game.UseOn
import com.github.benpollarduk.ktaf.extensions.equalsExaminable
import com.github.benpollarduk.ktaf.extensions.insensitiveEquals
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides an [Interpreter] for item commands.
 */
public class ItemCommandInterpreter : Interpreter {
    private fun tryParseDrop(text: String, game: Game): Drop? {
        val verb = CommonInterpretation.extractVerb(text)
        val noun = CommonInterpretation.extractNoun(text)
        return if (!verb.insensitiveEquals(DROP) && !verb.insensitiveEquals(DROP_SHORT)) {
            null
        } else {
            Drop(game.player.findItem(noun))
        }
    }

    private fun tryParseTake(text: String, game: Game): Command? {
        val verb = CommonInterpretation.extractVerb(text)
        val noun = CommonInterpretation.extractNoun(text)
        return if (!verb.insensitiveEquals(TAKE) && !verb.insensitiveEquals(TAKE_SHORT)) {
            null
        } else {
            var item: Item? = game.player.findItem(noun)
            return when {
                item != null -> {
                    Take(item)
                }
                noun.isEmpty() -> {
                    item = game.overworld.currentRegion?.currentRoom?.items?.firstOrNull {
                        it.isPlayerVisible && it.isTakeable
                    }
                    if (item != null) {
                        Unactionable("There are no takeable items in the room.")
                    }
                    Take(item)
                }
                noun.insensitiveEquals(ALL) -> {
                    TakeAll()
                }
                else -> {
                    item = game.overworld.currentRegion?.currentRoom?.findItem(noun)
                    if (item != null) {
                        Unactionable("There are no such item in the room.")
                    }
                    Take(item)
                }
            }
        }
    }

    @Suppress("ReturnCount", "CyclomaticComplexMethod")
    private fun tryParseExamine(text: String, game: Game): Command? {
        val verb = CommonInterpretation.extractVerb(text)
        val noun = CommonInterpretation.extractNoun(text)
        if (!verb.insensitiveEquals(EXAMINE) && !verb.insensitiveEquals(EXAMINE_SHORT)) {
            return null
        } else {
            if (noun.isEmpty()) {
                val room = game.overworld.currentRegion?.currentRoom
                return Examine(room)
            }

            var examinable: com.github.benpollarduk.ktaf.assets.Examinable? = game.player.findItem(noun)
            if (examinable != null) {
                return Examine(examinable)
            }

            examinable = game.overworld.currentRegion?.currentRoom?.findItem(noun)
            if (examinable != null) {
                return Examine(examinable)
            }

            examinable = game.overworld.currentRegion?.currentRoom?.findCharacter(noun)
            if (examinable != null) {
                return Examine(examinable)
            }

            val direction = CommonInterpretation.tryParseDirection(text)
            if (direction != null) {
                val room = game.overworld.currentRegion?.currentRoom
                val exit = room?.findExit(direction) ?: return Unactionable(
                    "There is no exit in this room to the $direction."
                )
                return Examine(exit)
            }

            if (noun.insensitiveEquals(ME) || noun.equalsExaminable(game.player)) {
                return Examine(game.player)
            }

            if (noun.insensitiveEquals(ROOM) || noun.equalsExaminable(game.overworld.currentRegion?.currentRoom)) {
                return Examine(game.overworld.currentRegion?.currentRoom)
            }

            if (noun.insensitiveEquals(REGION) || noun.equalsExaminable(game.overworld.currentRegion)) {
                return Examine(game.overworld.currentRegion)
            }

            if (noun.insensitiveEquals(OVERWORLD) || noun.equalsExaminable(game.overworld)) {
                return Examine(game.overworld)
            }

            return Examine(null)
        }
    }

    private fun tryParseUseOn(text: String, game: Game): Command? {
        val verb = CommonInterpretation.extractVerb(text)
        var noun = CommonInterpretation.extractNoun(text)

        if (!verb.insensitiveEquals(USE)) {
            return null
        }

        val target: InteractionTarget?
        var itemName: String
        val onPadded = " $ON "

        val currentRoom = game.overworld.currentRegion?.currentRoom

        if (noun.contains(onPadded, true)) {
            itemName = noun.substring(0, noun.indexOf(onPadded, 0, true))
            noun = noun.replace(itemName, "", true)
            val onIndex = noun.indexOf(onPadded, 0, true)
            val targetName = noun.substring(onIndex + onPadded.length)

            target = if (targetName.insensitiveEquals(ME) || targetName.equalsExaminable(game.player)) {
                game.player
            } else if (targetName.insensitiveEquals(ROOM) || targetName.equalsExaminable(currentRoom)) {
                currentRoom
            } else {
                game.findInteractionTarget(targetName)
            }

            if (target == null) {
                return Unactionable("$targetName is not a valid target.")
            }
        } else {
            target = currentRoom
            itemName = noun
        }

        val item = game.player.findItem(itemName) ?: currentRoom?.findItem(itemName)

        if (item != null) {
            return UseOn(item, target)
        }

        return Unactionable("You don't have that item.")
    }

    override val supportedCommands: List<CommandHelp>
        get() = ItemCommandInterpreter.supportedCommands

    override fun interpret(input: String, game: Game): InterpretationResult {
        var command: Command? = tryParseDrop(input, game)
        if (command != null) {
            return InterpretationResult(true, command)
        }

        command = tryParseExamine(input, game)
        if (command != null) {
            return InterpretationResult(true, command)
        }

        command = tryParseTake(input, game)
        if (command != null) {
            return InterpretationResult(true, command)
        }

        command = tryParseUseOn(input, game)
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

        commands.add(examineCommandHelp)

        if (game.player.items.any()) {
            commands.add(dropCommandHelp)
        }

        if (game.overworld.currentRegion?.currentRoom?.items?.any() == true) {
            commands.add(takeCommandHelp)
            commands.add(takeAllCommandHelp)
        }

        if (game.overworld.currentRegion?.currentRoom?.items?.any() == true || game.player.items.any()) {
            commands.add(useCommandHelp)
            commands.add(useOnCommandHelp)
        }

        return commands.toList()
    }

    public companion object {
        /**
         * Get the string for the [Drop] command.
         */
        public const val DROP: String = "Drop"

        /**
         * Get the string for the short [Drop] command.
         */
        public const val DROP_SHORT: String = "R"

        /**
         * Get the string for the [UseOn] command.
         */
        public const val USE: String = "Use"

        /**
         * Get the string for the on command.
         */
        public const val ON: String = "On"

        /**
         * Get the string for the [Take] command.
         */
        public const val TAKE: String = "Take"

        /**
         * Get the string for the short [Take] command.
         */
        public const val TAKE_SHORT: String = "T"

        /**
         * Get the string for the all command.
         */
        public const val ALL: String = "all"

        /**
         * Get the string for the [Examine] command.
         */
        public const val EXAMINE: String = "Examine"

        /**
         * Get the string for the short [Examine] command.
         */
        public const val EXAMINE_SHORT: String = "X"

        /**
         * Get the string for the me command.
         */
        public const val ME: String = "Me"

        /**
         * Get the string for the room command.
         */
        public const val ROOM: String = "Room"

        /**
         * Get the string for the region command.
         */
        public const val REGION: String = "Region"

        /**
         * Get the string for the overworld command.
         */
        public const val OVERWORLD: String = "overworld"

        /**
         * Get the string for the variable command.
         */
        public const val VARIABLE: String = "__"

        private val examineCommandHelp = CommandHelp(
            "$EXAMINE / $EXAMINE_SHORT $VARIABLE",
            "Examine anything in the game"
        )
        private val dropCommandHelp = CommandHelp(
            "$DROP / $DROP_SHORT $VARIABLE",
            "Drop an item"
        )
        private val takeCommandHelp = CommandHelp(
            "$TAKE / $TAKE_SHORT $VARIABLE",
            "Take an item"
        )
        private val takeAllCommandHelp = CommandHelp(
            "$TAKE / $TAKE_SHORT $ALL",
            "Take all items in the current room"
        )
        private val useCommandHelp = CommandHelp(
            "$USE $VARIABLE",
            "Use an item on the current room"
        )
        private val useOnCommandHelp = CommandHelp(
            "$USE $VARIABLE ${ON.lowercase()} $VARIABLE",
            "Use an item on another item or character"
        )

        /**
         * Get the supported commands.
         */
        public val supportedCommands: List<CommandHelp> = listOf(
            examineCommandHelp,
            takeCommandHelp,
            takeAllCommandHelp,
            useCommandHelp,
            useOnCommandHelp
        )
    }
}
