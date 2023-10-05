package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.commands.game.Move
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides an [Interpreter] for movement commands.
 */
public class MovementCommandInterpreter : Interpreter {
    override val supportedCommands: List<CommandHelp>
        get() = MovementCommandInterpreter.supportedCommands

    override fun interpret(input: String, game: Game): InterpretationResult {
        val direction = CommonInterpretation.tryParseDirection(input)
        if (direction != null) {
            return InterpretationResult(true, Move(direction))
        }

        return InterpretationResult.fail
    }

    override fun getContextualCommandHelp(game: Game): List<CommandHelp> {
        if (game.activeConverser?.conversation != null) {
            return emptyList()
        }

        val commands = mutableListOf<CommandHelp>()

        if (game.overworld.currentRegion?.currentRoom?.canMove(Direction.NORTH) == true) {
            commands.add(moveNorthCommandHelp)
        }

        if (game.overworld.currentRegion?.currentRoom?.canMove(Direction.EAST) == true) {
            commands.add(moveEastCommandHelp)
        }

        if (game.overworld.currentRegion?.currentRoom?.canMove(Direction.SOUTH) == true) {
            commands.add(moveSouthCommandHelp)
        }

        if (game.overworld.currentRegion?.currentRoom?.canMove(Direction.WEST) == true) {
            commands.add(moveWestCommandHelp)
        }

        if (game.overworld.currentRegion?.currentRoom?.canMove(Direction.UP) == true) {
            commands.add(moveUpCommandHelp)
        }

        if (game.overworld.currentRegion?.currentRoom?.canMove(Direction.DOWN) == true) {
            commands.add(moveDownCommandHelp)
        }

        return commands.toList()
    }

    public companion object {
        /**
         * Get the string for the [Move] north command.
         */
        public const val NORTH: String = "North"

        /**
         * Get the string for the short [Move] north command.
         */
        public const val NORTH_SHORT: String = "N"

        /**
         * Get the string for the [Move] east command.
         */
        public const val EAST: String = "East"

        /**
         * Get the string for the short [Move] east command.
         */
        public const val EAST_SHORT: String = "E"

        /**
         * Get the string for the [Move] south command.
         */
        public const val SOUTH: String = "South"

        /**
         * Get the string for the short [Move] south command.
         */
        public const val SOUTH_SHORT: String = "S"

        /**
         * Get the string for the [Move] west command.
         */
        public const val WEST: String = "West"

        /**
         * Get the string for the short [Move] west command.
         */
        public const val WEST_SHORT: String = "W"

        /**
         * Get the string for the [Move] up command.
         */
        public const val UP: String = "Up"

        /**
         * Get the string for the short [Move] up command.
         */
        public const val UP_SHORT: String = "U"

        /**
         * Get the string for the [Move] down command.
         */
        public const val DOWN: String = "Down"

        /**
         * Get the string for the short [Move] down command.
         */
        public const val DOWN_SHORT: String = "D"

        private val moveNorthCommandHelp = CommandHelp("$NORTH / $NORTH_SHORT", "Move north")
        private val moveEastCommandHelp = CommandHelp("$EAST / $EAST_SHORT", "Move east")
        private val moveSouthCommandHelp = CommandHelp("$SOUTH / $SOUTH_SHORT", "Move south")
        private val moveWestCommandHelp = CommandHelp("$WEST / $WEST_SHORT", "Move west")
        private val moveUpCommandHelp = CommandHelp("$UP / $UP_SHORT", "Move up")
        private val moveDownCommandHelp = CommandHelp("$DOWN / $DOWN_SHORT", "Move down")

        /**
         * Get the supported commands.
         */
        public val supportedCommands: List<CommandHelp> = listOf(
            moveNorthCommandHelp,
            moveEastCommandHelp,
            moveSouthCommandHelp,
            moveWestCommandHelp,
            moveUpCommandHelp,
            moveDownCommandHelp
        )
    }
}
