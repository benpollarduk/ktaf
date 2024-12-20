package com.github.benpollarduk.ktaf.assets.locations

import com.github.benpollarduk.ktaf.assets.ExaminationResult
import com.github.benpollarduk.ktaf.assets.ExaminationScene
import com.github.benpollarduk.ktaf.assets.Identifier
import com.github.benpollarduk.ktaf.extensions.equalsExaminable
import com.github.benpollarduk.ktaf.utilities.RegionMaker

/**
 * Provides a region which is a container of [Room]. The region has an [identifier] and a [description].
 */
public class Region(
    override var identifier: Identifier,
    override var description: com.github.benpollarduk.ktaf.assets.Description
) : com.github.benpollarduk.ktaf.assets.ExaminableObject() {
    /**
     * Provides a region which is a container of [Room]. The region has an [identifier] and a [description].
     */
    public constructor(
        identifier: String,
        description: String
    ) : this(Identifier(identifier), com.github.benpollarduk.ktaf.assets.Description(description))

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
     * Try and get a [Room] from a specified [name]. If a [Room] with a matching name cannot be found then null will
     * be returned.
     */
    public fun findRoom(name: String): Room? {
        return roomPositions.firstOrNull { name.equalsExaminable(it.room) }?.room
    }

    /**
     * Get the [RoomPosition] of a [room]. If the specified [room] does not exist in this [Region] null is returned.
     */
    public fun getPositionOfRoom(room: Room): RoomPosition? {
        val matrix = toMatrix()

        if (matrix.isEmpty) {
            return null
        }

        return roomPositions.firstOrNull { it.room == room }
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
     * Set a [Room] specified by [roomName] as the start room.
     */
    public fun setStartRoom(roomName: String) {
        val room = roomPositions.firstOrNull { roomName.equalsExaminable(it.room) }?.room
        if (room != null) {
            currentRoom = room
            room.movedInto(null)
        }
    }

    /**
     * Convert this to a [Matrix].
     */
    public fun toMatrix(): Matrix {
        return RegionMaker.convertToRoomMatrix(roomPositions)
    }

    /**
     * Jump to specified [room]. If the jump was possible true is returned, else false.
     */
    public fun jumpToRoom(room: Room): Boolean {
        val roomPosition = roomPositions.firstOrNull { it.room == room } ?: return false
        currentRoom = roomPosition.room
        return true
    }

    /**
     * Jump to a [Room] specified by [roomName]. If the jump was possible true is returned, else false.
     */
    public fun jumpToRoom(roomName: String): Boolean {
        val room = roomPositions.firstOrNull { roomName.equalsExaminable(it.room) }?.room ?: return false
        return jumpToRoom(room)
    }

    override fun examine(scene: ExaminationScene): ExaminationResult {
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
