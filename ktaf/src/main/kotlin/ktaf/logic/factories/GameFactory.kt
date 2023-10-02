package ktaf.logic.factories

import ktaf.logic.Game

/**
 * Provides a lambda signature for a factory for creating instances of [Game].
 */
public typealias GameFactory = () -> Game
