package ktaf.commands

import ktaf.assets.PlayerVisible
import ktaf.assets.interaction.Reaction
import ktaf.interpretation.CommandHelp
import ktaf.logic.Game

/**
 * Provides a mechanism for allowing custom commands to be added. [commandHelp] provides help for the command,
 * [isPlayerVisible] allows the visible state for the player to be toggled. [callback] provides a
 * [CustomAction] that is invoked when the [Command] is invoked.
 */
public class CustomCommand(
    public val commandHelp: CommandHelp,
    public override var isPlayerVisible: Boolean,
    private val callback: CustomAction
) : Command, PlayerVisible {

    /**
     * Any arguments to be provided when the [CustomCommand] is invoked.
     */
    public var arguments: List<String> = emptyList()

    override fun invoke(game: Game): Reaction {
        return callback.invoke(game, arguments)
    }
}
