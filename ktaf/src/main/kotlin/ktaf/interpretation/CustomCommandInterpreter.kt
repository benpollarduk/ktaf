package ktaf.interpretation

import ktaf.commands.CustomCommand
import ktaf.extensions.insensitiveEquals
import ktaf.logic.Game

/**
 * Provides an [Interpreter] for [CustomCommand].
 */
public class CustomCommandInterpreter : Interpreter {
    override val supportedCommands: List<CommandHelp>
        get() = emptyList()

    override fun interpret(input: String, game: Game): InterpretationResult {
        if (input.isEmpty()) {
            return InterpretationResult.fail
        }

        val entries = input.split(' ')
        val commandName = entries.first()
        val args = entries.filter { it != commandName }
        val commands = mutableListOf<CustomCommand>()

        game.getAllPlayerVisibleExaminables().filter { it.commands.any() }.forEach {
            commands.addAll(it.commands)
        }

        var command = commands.firstOrNull { it.commandHelp.command.insensitiveEquals(commandName) }

        if (command != null) {
            command.arguments = args
            return InterpretationResult(true, command)
        }

        // command may have a space in it...
        command = commands.firstOrNull { it.commandHelp.command.insensitiveEquals(input) }

        return if (command == null) {
            InterpretationResult.fail
        } else {
            InterpretationResult(true, command)
        }
    }

    override fun getContextualCommandHelp(game: Game): List<CommandHelp> {
        if (game.activeConverser?.conversation != null) {
            return emptyList()
        }

        val commands = mutableListOf<CommandHelp>()
        val examinables = game.getAllPlayerVisibleExaminables().filter { it.commands.any() }
        examinables.forEach { examinable ->
            val commandHelps = examinable.commands.filter { it.isPlayerVisible }.map { it.commandHelp }
            commands.addAll(commandHelps)
        }

        return commands.toList()
    }
}
