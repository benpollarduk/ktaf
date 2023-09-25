package ktaf.conversations

import ktaf.logic.Game

/**
 * Provides a mechanism for invoking actions in a conversation within a specified [game].
 */
public typealias ConversationAction = (game: Game) -> Unit
