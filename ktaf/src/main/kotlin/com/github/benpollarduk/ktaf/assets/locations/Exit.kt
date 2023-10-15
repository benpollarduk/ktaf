package com.github.benpollarduk.ktaf.assets.locations

import com.github.benpollarduk.ktaf.assets.ConditionalDescription
import com.github.benpollarduk.ktaf.assets.Description
import com.github.benpollarduk.ktaf.assets.ExaminableObject

public class Exit(
    public val direction: Direction,
    private var locked: Boolean = false,
    description: Description? = null
) : ExaminableObject() {
    init {
        if (description == null) {
            this.description = ConditionalDescription(
                "The exit ${direction.toString().lowercase()} is locked.",
                "The exit ${direction.toString().lowercase()} is unlocked."
            ) { this.isLocked }
        }
    }

    public val isLocked: Boolean
        get() = locked

    /**
     * Unlock this [Exit].
     */
    public fun unlock() {
        locked = false
    }

    /**
     * Lock this [Exit].
     */
    public fun lock() {
        locked = true
    }
}
