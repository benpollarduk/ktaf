package ktaf.utilities

import ktaf.assets.Description
import ktaf.assets.Identifier
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Matrix
import ktaf.assets.locations.Region
import ktaf.assets.locations.Room
import ktaf.assets.locations.RoomPosition

/**
 * A helper for making a [Region], with a specified [identifier] and [description].
 */
public class RegionMaker(
    private val identifier: Identifier,
    private val description: Description
) {
    /**
     * A helper for making a [Region], with a specified [identifier] and [description].
     */
    public constructor(identifier: String, description: String) : this(Identifier(identifier), Description(description))

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
        val region = Region(identifier, description)
        val matrix = convertToRoomMatrix(rooms)

        for (depth in 0 until matrix.depth) {
            for (row in 0 until matrix.height) {
                for (column in 0 until matrix.width) {
                    val room = matrix[column, row, depth]
                    if (room != Room.empty) {
                        region.addRoom(room, column, row, depth)
                    }
                }
            }
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
            region.toMatrix().toRooms().forEach {
                if (it != Room.empty) {
                    for (direction in Region.allDirections) {
                        val exit = it.findExit(direction, true)
                        if (exit != null) {
                            val adjoining = region.getAdjoiningRoom(direction, it)
                            val inverseDirection = direction.inverse()

                            if (adjoining != null) {
                                if (adjoining.findExit(inverseDirection, true) == null) {
                                    adjoining.addExit(Exit(inverseDirection, exit.isLocked))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
