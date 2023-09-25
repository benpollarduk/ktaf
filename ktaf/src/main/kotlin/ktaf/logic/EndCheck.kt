package ktaf.logic

/**
 * Provides a lambda signature for a determining if a specified [game] has ended.
 */
public typealias EndCheck = (game: Game) -> EndCheckResult
