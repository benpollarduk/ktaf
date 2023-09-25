package ktaf.commands

import ktaf.assets.interaction.Reaction
import ktaf.logic.Game

/**
 * Provides a lambda signature for a custom command to obtain a [Reaction].
 * The callback is invoked against a [game] with the specified [arguments].
 */
public typealias CustomAction = (game: Game, arguments: List<String>) -> Reaction
