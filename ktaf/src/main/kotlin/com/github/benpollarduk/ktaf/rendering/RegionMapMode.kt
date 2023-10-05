package com.github.benpollarduk.ktaf.rendering

/**
 * Provides an enumeration of map modes for regions.
 */
public enum class RegionMapMode {
    /**
     * Displays regions in detail.
     */
    DETAILED,

    /**
     * Displays rooms in a region as a single character which allows larger region maps to be displayed in a limited
     * area.
     */
    UNDETAILED,

    /**
     *  Displays detailed region maps if there is room, else undetailed maps will be diplayed.
     */
    DYNAMIC
}
