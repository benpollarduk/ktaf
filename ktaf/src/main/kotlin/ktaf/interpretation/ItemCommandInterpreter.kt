package ktaf.interpretation

import ktaf.assets.Examinable
import ktaf.assets.Item
import ktaf.assets.interaction.InteractionTarget
import ktaf.commands.Command
import ktaf.commands.game.Drop
import ktaf.commands.game.Examine
import ktaf.commands.game.Take
import ktaf.commands.game.TakeAll
import ktaf.commands.game.Unactionable
import ktaf.commands.game.UseOn
import ktaf.extensions.equalsExaminable
import ktaf.extensions.insensitiveEquals
import ktaf.logic.Game
import java.util.*

/**
 * Provides an [Interpreter] for item commands.
 */
public class ItemCommandInterpreter : Interpreter {
    private fun tryParseDrop(text: String, game: Game): Drop? {
        val verb = CommonInterpretation.extractVerb(text)
        val noun = CommonInterpretation.extractNoun(text)
        return if (!verb.insensitiveEquals(drop) && !verb.insensitiveEquals(dropShort)) {
            null
        } else {
            Drop(game.player.findItem(noun))
        }
    }

    private fun tryParseTake(text: String, game: Game): Command? {
        val verb = CommonInterpretation.extractVerb(text)
        val noun = CommonInterpretation.extractNoun(text)
        return if (!verb.insensitiveEquals(take) && !verb.insensitiveEquals(takeShort)) {
            null
        } else {
            var item: Item? = game.player.findItem(noun)
            return when {
                item != null -> {
                    Take(item)
                }
                noun.isEmpty() -> {
                    item = game.overworld.currentRegion?.currentRoom?.items?.firstOrNull { it.isPlayerVisible && it.isTakeable }
                    if (item != null) {
                        Unactionable("There are no takeable items in the room.")
                    }
                    Take(item)
                }
                noun.insensitiveEquals(all) -> {
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

    private fun tryParseExamine(text: String, game: Game): Command? {
        val verb = CommonInterpretation.extractVerb(text)
        val noun = CommonInterpretation.extractNoun(text)
        if (!verb.insensitiveEquals(examine) && !verb.insensitiveEquals(examineShort)) {
            return null
        } else {
            if (noun.isEmpty()) {
                val room = game.overworld.currentRegion?.currentRoom
                return Examine(room)
            }

            var examinable: Examinable? = game.player.findItem(noun)
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
                val exit = game.overworld.currentRegion?.currentRoom?.findExit(direction) ?: return Unactionable("There is no exit in this room to the $direction.")
                return Examine(exit)
            }

            if (noun.insensitiveEquals(me) || noun.equalsExaminable(game.player)) {
                return Examine(game.player)
            }

            if (noun.insensitiveEquals(room) || noun.equalsExaminable(game.overworld.currentRegion?.currentRoom)) {
                return Examine(game.overworld.currentRegion?.currentRoom)
            }

            if (noun.insensitiveEquals(region) || noun.equalsExaminable(game.overworld.currentRegion)) {
                return Examine(game.overworld.currentRegion)
            }

            if (noun.insensitiveEquals(overworld) || noun.equalsExaminable(game.overworld)) {
                return Examine(game.overworld)
            }

            return Examine(null)
        }
    }

    private fun tryParseUseOn(text: String, game: Game): Command? {
        val verb = CommonInterpretation.extractVerb(text)
        var noun = CommonInterpretation.extractNoun(text)

        if (!verb.insensitiveEquals(use)) {
            return null
        }

        val target: InteractionTarget?
        var itemName: String
        val onPadded = " $on "

        val currentRoom = game.overworld.currentRegion?.currentRoom

        if (noun.contains(onPadded, true)) {
            itemName = noun.substring(0, noun.indexOf(onPadded, 0, true))
            noun = noun.replace(itemName, "", true)
            val onIndex = noun.indexOf(onPadded, 0, true)
            val targetName = noun.substring(onIndex + onPadded.length)

            target = if (targetName.insensitiveEquals(me) || targetName.equalsExaminable(game.player)) {
                game.player
            } else if (targetName.insensitiveEquals(room) || targetName.equalsExaminable(currentRoom)) {
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
        public const val drop: String = "Drop"

        /**
         * Get the string for the short [Drop] command.
         */
        public const val dropShort: String = "R"

        /**
         * Get the string for the [UseOn] command.
         */
        public const val use: String = "Use"

        /**
         * Get the string for the on command.
         */
        public const val on: String = "On"

        /**
         * Get the string for the [Take] command.
         */
        public const val take: String = "Take"

        /**
         * Get the string for the short [Take] command.
         */
        public const val takeShort: String = "T"

        /**
         * Get the string for the all command.
         */
        public const val all: String = "all"

        /**
         * Get the string for the [Examine] command.
         */
        public const val examine: String = "Examine"

        /**
         * Get the string for the short [Examine] command.
         */
        public const val examineShort: String = "X"

        /**
         * Get the string for the me command.
         */
        public const val me: String = "Me"

        /**
         * Get the string for the room command.
         */
        public const val room: String = "Room"

        /**
         * Get the string for the region command.
         */
        public const val region: String = "Region"

        /**
         * Get the string for the overworld command.
         */
        public const val overworld: String = "overworld"

        /**
         * Get the string for the variable command.
         */
        public const val variable: String = "__"

        private val examineCommandHelp = CommandHelp("$examine / $examineShort $variable", "Examine anything in the game")
        private val dropCommandHelp = CommandHelp("$drop / $dropShort $variable", "Drop an item")
        private val takeCommandHelp = CommandHelp("$take / $takeShort $variable", "Take an item")
        private val takeAllCommandHelp = CommandHelp("$take / $takeShort $all", "Take all items in the current room")
        private val useCommandHelp = CommandHelp("$use $variable", "Use an item on the current room")
        private val useOnCommandHelp = CommandHelp("$use $variable ${on.lowercase()} $variable", "Use an item on another item or character")

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
