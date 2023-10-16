package com.github.benpollarduk.ktaf.utilities

import com.github.benpollarduk.ktaf.assets.Description
import com.github.benpollarduk.ktaf.assets.Identifier
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Matrix
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.RoomPosition

/**
 * A helper for making a [Region], with a specified [identifier] and [description].
 */
public class RegionMaker(
    private val identifier: Identifier,
    private val description: com.github.benpollarduk.ktaf.assets.Description
) {
    /**
     * A helper for making a [Region], with a specified [identifier] and [description].
     */
    public constructor(identifier: String, description: String) : this(
        Identifier(identifier),
        com.github.benpollarduk.ktaf.assets.Description(description)
    )

    /**
     * Get the [Room] at the specified [x], [y] and [z] position. If no [Room] exists in that location then null is
     * returned.
     */
    public operator fun get(x: Int, y: Int, z: Int): Room? {
        return rooms.firstOrNull { it.isAtPosition(x, y, z) }?.room
    }

    /**
     * Sets the [value] at the specified [x], [y] and [z] position.
     */
    public operator fun set(x: Int, y: Int, z: Int, value: Room) {
        val element = rooms.firstOrNull { it.isAtPosition(x, y, z) }

        if (element != null) {
            rooms.remove(element)
        }

        rooms.add(RoomPosition(value, x, y, z))
    }

    private val rooms: MutableList<RoomPosition> = mutableListOf()

    /**
     * Make a new [Region].
     */
    public fun make(): Region {
        return make(rooms.first())
    }

    /**
     * Make a new [Region] with a specified [startPosition].
     */
    public fun make(startPosition: RoomPosition): Region {
        return make(startPosition.x, startPosition.y, startPosition.z)
    }

    /**
     * Make a new [Region] with a specified [x], [y] and [z] start position.
     */
    public fun make(x: Int, y: Int, z: Int): Region {
        // conversion to matrix normalises room positions to be 0 indexed regardless of how they were originally specified
        val matrix = convertToRoomMatrix(rooms)
        val rooms = matrix.toRoomPositions()
        val region = Region(identifier, description)

        rooms.filter { it.room != Room.empty }.forEach {
            region.addRoom(it.room, it.x, it.y, it.z)
        }

        linkExits(region)

        // offset start room, matrix will have normalised positions
        val minX = rooms.minByOrNull { it.x }?.x ?: 0
        val minY = rooms.minByOrNull { it.y }?.y ?: 0
        val minZ = rooms.minByOrNull { it.z }?.z ?: 0

        region.setStartRoom(x - minX, y - minY, z - minZ)
        return region
    }

    /**
     * Determines if a [Room] can be placed at a specified [x], [y] and [z] position.
     */
    public fun canPlaceRoom(x: Int, y: Int, z: Int): Boolean {
        return rooms.all { !it.isAtPosition(x, y, z) }
    }

    /**
     * Get all of the registered [RoomPosition].
     */
    public fun getRoomPositions(): List<RoomPosition> {
        return rooms.toList()
    }

    internal companion object {
        /**
         * Convert a list of [roomPositions] to a [Matrix].
         */
        internal fun convertToRoomMatrix(roomPositions: List<RoomPosition>): Matrix {
            if (roomPositions.isEmpty()) {
                return Matrix(Array(0) { Array(0) { Array(0) { Room.empty } } })
            }

            val minX = roomPositions.minOf { it.x }
            val minY = roomPositions.minOf { it.y }
            val minZ = roomPositions.minOf { it.z }
            val maxX = roomPositions.maxOf { it.x }
            val maxY = roomPositions.maxOf { it.y }
            val maxZ = roomPositions.maxOf { it.z }

            val lengthX = (maxX - minX) + 1
            val lengthY = (maxY - minY) + 1
            val lengthZ = (maxZ - minZ) + 1

            val normalisedOffsetX = 0 - minX
            val normalisedOffsetY = 0 - minY
            val normalisedOffsetZ = 0 - minZ

            val matrix = Array(lengthX) { Array(lengthY) { Array(lengthZ) { Room.empty } } }

            for (roomPosition in roomPositions) {
                val x = roomPosition.x + normalisedOffsetX
                val y = roomPosition.y + normalisedOffsetY
                val z = roomPosition.z + normalisedOffsetZ
                matrix[x][y][z] = roomPosition.room
            }

            return Matrix(matrix)
        }

        /**
         * Link exits in a [region], ensuring that when an [Exit] exists in one [Room] its neighbour also has one.
         */
        internal fun linkExits(region: Region) {
            region.toMatrix()
                .toRooms()
                .filter { it != Room.empty }
                .forEach {
                    for (direction in Region.allDirections) {
                        val exit = it.findExit(direction, true) ?: continue
                        val adjoining = region.getAdjoiningRoom(direction, it)
                        val inverseDirection = direction.inverse()

                        if (adjoining != null && (adjoining.findExit(inverseDirection, true) == null)) {
                            adjoining.addExit(Exit(inverseDirection, exit.isLocked))
                        }
                    }
                }
        }
    }
}
