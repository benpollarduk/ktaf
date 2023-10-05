package com.github.benpollarduk.ktaf.assets.locations

public class ViewPoint(region: Region) {
    private val surroundingRooms: MutableMap<Direction, Room> = mutableMapOf()
    init {
        val room = region.currentRoom
        if (room != null) {
            val map = mutableMapOf<Direction, Room>()

            for (direction in Region.allDirections) {
                if (room.findExit(direction, false) != null) {
                    val adjoiningRoom = region.getAdjoiningRoom(direction)
                    if (adjoiningRoom != null) {
                        map[direction] = adjoiningRoom
                    }
                }
            }

            map.forEach {
                surroundingRooms[it.key] = it.value
            }
        }
    }

    public operator fun get(direction: Direction): Room? {
        return if (direction in surroundingRooms) {
            surroundingRooms[direction]
        } else {
            null
        }
    }

    public val any: Boolean
        get() = surroundingRooms.any()

    public val anyVisited: Boolean
        get() = surroundingRooms.any { it.value.hasBeenVisited }

    public val anyNotVisited: Boolean
        get() = surroundingRooms.any { !it.value.hasBeenVisited }

    public companion object {
        public val noView: ViewPoint = ViewPoint(Region("", ""))
    }
}
