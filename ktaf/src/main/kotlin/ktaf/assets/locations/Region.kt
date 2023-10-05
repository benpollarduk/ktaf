package ktaf.assets.locations

import ktaf.assets.Description
import ktaf.assets.ExaminableObject
import ktaf.assets.ExaminationResult
import ktaf.assets.Identifier
import ktaf.utilities.RegionMaker

/**
 * Provides a region which is a container of [Room]. The region has an [identifier] and a [description].
 */
public class Region(
    override var identifier: Identifier,
    override var description: Description
) : ExaminableObject() {
    /**
     * Provides a region which is a container of [Room]. The region has an [identifier] and a [description].
     */
    public constructor(
        identifier: String,
        description: String
    ) : this(Identifier(identifier), Description(description))

    /**
     * Get a [Room] at the specified [x], [y] and [z] location.
     */
    public operator fun get(x: Int, y: Int, z: Int): Room? {
        return roomPositions.firstOrNull { it.isAtPosition(x, y, z) }?.room
    }

    private var _currentRoom: Room? = null
    private val roomPositions: MutableList<RoomPosition> = mutableListOf()

    /**
     * Get the number of rooms in this [Region].
     */
    public val rooms: Int
        get() = roomPositions.size

    /**
     * Get the current room.
     */
    public var currentRoom: Room?
        get() {
            if (_currentRoom != null) {
                return _currentRoom
            }

            if (roomPositions.isNotEmpty()) {
                val first = roomPositions.first().room
                setStartRoom(first)
                _currentRoom = first
            }

            return _currentRoom
        }
        private set(value) {
            _currentRoom = value
        }

    /**
     * Specifies if this [Region] is visible without first being discovered.
     */
    public var visibleWithoutDiscovery: Boolean = false

    /**
     * Get the [RoomPosition] of a [room]. If the specified [room] does not exist in this [Region] null is returned.
     */
    public fun getPositionOfRoom(room: Room): RoomPosition? {
        val matrix = toMatrix()

        if (matrix.isEmpty) {
            return null
        }

        val rooms = matrix.toRoomPositions()
        return rooms.firstOrNull { it.room == room }
    }

    /**
     * Add a [room] at a specified [x], [y] and [z] location. Returns true if the [Room] could be added, else false.
     */
    public fun addRoom(room: Room, x: Int, y: Int, z: Int): Boolean {
        val addable = !roomPositions.any { it.room == room || it.isAtPosition(x, y, z) }
        if (addable) {
            roomPositions.add(RoomPosition(room, x, y, z))
        }
        return addable
    }

    /**
     * Get the adjoining room in a specified [direction], from the [currentRoom].
     */
    public fun getAdjoiningRoom(direction: Direction): Room? {
        return getAdjoiningRoom(direction, currentRoom)
    }

    /**
     * Get the adjoining room in a specified [direction] from a specified [room].
     */
    public fun getAdjoiningRoom(direction: Direction, room: Room?): Room? {
        if (room == null) {
            return null
        }

        val position = roomPositions.firstOrNull { it.room == room } ?: return null
        val next = nextPosition(position.x, position.y, position.z, direction)
        return this[next.x, next.y, next.z]
    }

    /**
     * Move in a specified [direction]. Returns true if the move was possible, else false.
     */
    public fun move(direction: Direction): Boolean {
        if (currentRoom?.canMove(direction) != true) {
            return false
        }

        val adjoiningRoom = getAdjoiningRoom(direction) ?: return false
        adjoiningRoom.movedInto(direction.inverse())
        currentRoom = adjoiningRoom
        return true
    }

    /**
     * Unlock an [Exit] in the specified [direction]. Also unlocks the opposing [Exit] on the adjoining room. If this
     * is possible return true, else false.
     */
    @Suppress("ReturnCount")
    public fun unlockExitPair(direction: Direction): Boolean {
        val current = currentRoom ?: return false

        if (roomPositions.none { it.room == current }) {
            return false
        }

        val exitInRoom = current[direction] ?: return false
        val adjoiningRoom = getAdjoiningRoom(direction) ?: return false
        val adjoiningExit = adjoiningRoom.findExit(direction.inverse(), true) ?: return false
        adjoiningExit.unlock()
        exitInRoom.unlock()
        return true
    }

    /**
     * Set a specified [room] as the start room.
     */
    public fun setStartRoom(room: Room) {
        currentRoom = room
        room.movedInto(null)
    }

    /**
     * Set a [Room] at a specified [x], [y] and [z] position as the start room.
     */
    public fun setStartRoom(x: Int, y: Int, z: Int) {
        var room = roomPositions.firstOrNull { it.isAtPosition(x, y, z) }?.room
        setStartRoom(room ?: roomPositions.first().room)
    }

    /**
     * Convert this to a [Matrix].
     */
    public fun toMatrix(): Matrix {
        return RegionMaker.convertToRoomMatrix(roomPositions)
    }

    /**
     * Jump to a [Room] at a specified [x], [y] and [z] position.
     */
    public fun jumpToRoom(x: Int, y: Int, z: Int): Boolean {
        val roomPosition = roomPositions.firstOrNull { it.isAtPosition(x, y, z) } ?: return false
        currentRoom = roomPosition.room
        return true
    }

    override fun examine(): ExaminationResult {
        return ExaminationResult("$Identifier: ${description.getDescription()}")
    }

    public companion object {
        /**
         * Get a next position in a [direction] given a current [x], [y] and [z] position.
         */
        internal fun nextPosition(x: Int, y: Int, z: Int, direction: Direction): Point3D {
            return when (direction) {
                Direction.NORTH -> Point3D(x, y + 1, z)
                Direction.SOUTH -> Point3D(x, y - 1, z)
                Direction.EAST -> Point3D(x + 1, y, z)
                Direction.WEST -> Point3D(x - 1, y, z)
                Direction.UP -> Point3D(x, y, z + 1)
                Direction.DOWN -> Point3D(x, y, z - 1)
            }
        }

        /**
         * Get a list of all directions.
         */
        public val allDirections: List<Direction> = listOf(
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
            Direction.UP,
            Direction.DOWN
        )
    }
}
