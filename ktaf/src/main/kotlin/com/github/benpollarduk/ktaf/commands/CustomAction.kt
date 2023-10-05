package com.github.benpollarduk.ktaf.commands

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a lambda signature for a custom command to obtain a [Reaction].
 * The callback is invoked against a [game] with the specified [arguments].
 */
public typealias CustomAction = (game: Game, arguments: List<String>) -> Reaction
