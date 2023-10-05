package com.github.benpollarduk.ktaf.assets

/**
 * Provides a simple contract for any object that can be visible to a player.
**/
public interface PlayerVisible {
    /**
     * Returns true if visible to the player, else false.
     */
    public val isPlayerVisible: Boolean
}
