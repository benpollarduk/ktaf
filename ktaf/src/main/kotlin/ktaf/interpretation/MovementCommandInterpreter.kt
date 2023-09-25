package ktaf.interpretation

import ktaf.assets.locations.Direction
import ktaf.commands.game.Move
import ktaf.logic.Game

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
        public const val north: String = "North"

        /**
         * Get the string for the short [Move] north command.
         */
        public const val northShort: String = "N"

        /**
         * Get the string for the [Move] east command.
         */
        public const val east: String = "East"

        /**
         * Get the string for the short [Move] east command.
         */
        public const val eastShort: String = "E"

        /**
         * Get the string for the [Move] south command.
         */
        public const val south: String = "South"

        /**
         * Get the string for the short [Move] south command.
         */
        public const val southShort: String = "S"

        /**
         * Get the string for the [Move] west command.
         */
        public const val west: String = "West"

        /**
         * Get the string for the short [Move] west command.
         */
        public const val westShort: String = "W"

        /**
         * Get the string for the [Move] up command.
         */
        public const val up: String = "Up"

        /**
         * Get the string for the short [Move] up command.
         */
        public const val upShort: String = "U"

        /**
         * Get the string for the [Move] down command.
         */
        public const val down: String = "Down"

        /**
         * Get the string for the short [Move] down command.
         */
        public const val downShort: String = "D"

        private val moveNorthCommandHelp = CommandHelp("$north / $northShort", "Move north")
        private val moveEastCommandHelp = CommandHelp("$east / $eastShort", "Move east")
        private val moveSouthCommandHelp = CommandHelp("$south / $southShort", "Move south")
        private val moveWestCommandHelp = CommandHelp("$west / $westShort", "Move west")
        private val moveUpCommandHelp = CommandHelp("$up / $upShort", "Move up")
        private val moveDownCommandHelp = CommandHelp("$down / $downShort", "Move down")

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
