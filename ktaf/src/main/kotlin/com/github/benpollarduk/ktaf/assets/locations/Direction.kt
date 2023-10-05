package com.github.benpollarduk.ktaf.assets.locations

/**
 * Enumeration of directions.
 */
public enum class Direction {
    /**
     * North.
     */
    NORTH,

    /**
     * East.
     */
    EAST,

    /**
     * South.
     */
    SOUTH,

    /**
     * West.
     */
    WEST,

    /**
     * Up.
     */
    UP,

    /**
     * Down.
     */
    DOWN

    ;

    /**
     * Invert this [Direction].
     */
    public fun inverse(): Direction {
        return when (this) {
            NORTH -> SOUTH
            SOUTH -> NORTH
            EAST -> WEST
            WEST -> EAST
            UP -> DOWN
            DOWN -> UP
        }
    }
}
