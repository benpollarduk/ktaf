package com.github.benpollarduk.ktaf.assets.locations

/**
 * Provides a 3D matrix for representing collections of [Room], specified with the [rooms] parameter.
 */
public class Matrix(private var rooms: Array<Array<Array<Room>>>) {
    /**
     * Get a [Room] at a specified [x], [y] and [z] location.
     */
    public operator fun get(x: Int, y: Int, z: Int): Room {
        val xError = x < 0 || x >= width
        val yError = y < 0 || y >= height
        val zError = z < 0 || z >= depth

        if (xError || yError || zError) {
            return Room.empty
        }

        return rooms[x][y][z]
    }

    /**
     * Get the width of this [Matrix].
     */
    public val width: Int
        get() = if (rooms.isNotEmpty()) rooms.size else 0

    /**
     * Get the height of this [Matrix].
     */
    public val height: Int
        get() = if (rooms.isNotEmpty() && rooms[0].isNotEmpty()) rooms[0].size else 0

    /**
     * Get the depth of this [Matrix].
     */
    public val depth: Int
        get() = if (rooms.isNotEmpty() && rooms[0].isNotEmpty() && rooms[0][0].isNotEmpty()) rooms[0][0].size else 0

    /**
     * Get if this is empty.
     */
    public val isEmpty: Boolean
        get() = rooms.isEmpty()

    /**
     * Get this [Matrix] as a simple [List<RoomPosition>].
     */
    public fun toRoomPositions(): List<RoomPosition> {
        val roomList = mutableListOf<RoomPosition?>()

        for (z in 0 until depth) {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    roomList.add(RoomPosition(this[x, y, z], x, y, z))
                }
            }
        }

        return roomList
            .filterNotNull()
            .filter { it.room != Room.empty }
    }
}
