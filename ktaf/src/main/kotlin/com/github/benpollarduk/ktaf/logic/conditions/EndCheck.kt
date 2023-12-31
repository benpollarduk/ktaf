package com.github.benpollarduk.ktaf.logic.conditions

import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a lambda signature for a determining if a specified [game] has ended.
 */
public typealias EndCheck = (game: Game) -> EndCheckResult
