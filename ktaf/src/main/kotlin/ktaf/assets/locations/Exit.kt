package ktaf.assets.locations

import ktaf.ExaminableObject
import ktaf.assets.ConditionalDescription
import ktaf.assets.Description
import java.util.*

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
