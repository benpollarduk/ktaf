package com.github.benpollarduk.ktaf.conversations

import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a mechanism for invoking actions in a conversation within a specified [game].
 */
public typealias ConversationAction = (game: Game) -> Unit
