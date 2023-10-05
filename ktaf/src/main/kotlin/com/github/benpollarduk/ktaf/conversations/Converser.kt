package com.github.benpollarduk.ktaf.conversations

import com.github.benpollarduk.ktaf.assets.Examinable

/**
 * Provides a contract for any onject that can hold a [Conversation].
 */
public interface Converser : com.github.benpollarduk.ktaf.assets.Examinable {
    /**
     * This [Converser] [Conversation].
     */
    public val conversation: Conversation
}
