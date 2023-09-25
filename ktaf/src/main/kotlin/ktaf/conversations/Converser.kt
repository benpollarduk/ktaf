package ktaf.conversations

import ktaf.assets.Examinable

/**
 * Provides a contract for any onject that can hold a [Conversation].
 */
public interface Converser : Examinable {
    /**
     * This [Converser] [Conversation].
     */
    public val conversation: Conversation
}
