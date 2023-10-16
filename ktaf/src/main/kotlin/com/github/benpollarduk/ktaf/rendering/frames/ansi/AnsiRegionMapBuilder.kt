package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Matrix
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.RoomPosition
import com.github.benpollarduk.ktaf.rendering.FramePosition

/**
 * Provides an ANSI region map builder.
 */
@Suppress("LongParameterList", "LongMethod", "CyclomaticComplexMethod")
public class AnsiRegionMapBuilder(
    private val lockedExit: Char = 'x',
    private val unlockedExit: Char = ' ',
    private val emptySpace: Char = ' ',
    private val verticalBoundary: Char = '|',
    private val horizontalBoundary: Char = '-',
    private val lowerLevel: Char = '.',
    private val up: Char = '^',
    private val down: Char = 'v',
    private val player: Char = 'O',
    private val currentFloorIndicator: Char = '*',
    private val visitedBoundaryColor: AnsiColor = AnsiColor.WHITE,
    private val unvisitedBoundaryColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val playerColor: AnsiColor = AnsiColor.BLUE,
    private val lockedExitColor: AnsiColor = AnsiColor.RED,
    private val lowerFloorColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val showLowerFloors: Boolean = true
) {
    private fun drawCurrentFloorRoom(
        room: Room,
        left: Int,
        top: Int,
        isCurrent: Boolean,
        ansiGridStringBuilder: AnsiGridStringBuilder
    ) {
        /*
         * |   |
         *  ^Ov|
         * |---|
         */

        val color: AnsiColor = if (room.hasBeenVisited) visitedBoundaryColor else unvisitedBoundaryColor

        ansiGridStringBuilder.setCell(left, top, verticalBoundary, color)

        when {
            room.hasLockedExitInDirection(Direction.NORTH) -> {
                ansiGridStringBuilder.setCell(left + 1, top, lockedExit, lockedExitColor)
                ansiGridStringBuilder.setCell(left + 2, top, lockedExit, lockedExitColor)
                ansiGridStringBuilder.setCell(left + 3, top, lockedExit, lockedExitColor)
            }
            room.hasUnlockedExitInDirection(Direction.NORTH) -> {
                ansiGridStringBuilder.setCell(left + 2, top, unlockedExit, color)
                ansiGridStringBuilder.setCell(left + 3, top, unlockedExit, color)
                ansiGridStringBuilder.setCell(left + 1, top, unlockedExit, color)
            }
            else -> {
                ansiGridStringBuilder.setCell(left + 1, top, horizontalBoundary, color)
                ansiGridStringBuilder.setCell(left + 2, top, horizontalBoundary, color)
                ansiGridStringBuilder.setCell(left + 3, top, horizontalBoundary, color)
            }
        }

        ansiGridStringBuilder.setCell(left + 4, top, verticalBoundary, color)

        when {
            room.hasLockedExitInDirection(Direction.WEST) -> {
                ansiGridStringBuilder.setCell(left, top + 1, lockedExit, lockedExitColor)
            }
            room.hasUnlockedExitInDirection(Direction.WEST) -> {
                ansiGridStringBuilder.setCell(left, top + 1, unlockedExit, color)
            }
            else -> {
                ansiGridStringBuilder.setCell(left, top + 1, verticalBoundary, color)
            }
        }

        when {
            room.hasLockedExitInDirection(Direction.UP) -> {
                ansiGridStringBuilder.setCell(left + 1, top + 1, lockedExit, lockedExitColor)
            }
            room.hasUnlockedExitInDirection(Direction.UP) -> {
                ansiGridStringBuilder.setCell(left + 1, top + 1, up, color)
            }
            else -> {
                ansiGridStringBuilder.setCell(left + 1, top + 1, emptySpace, color)
            }
        }

        if (isCurrent) {
            ansiGridStringBuilder.setCell(left + 2, top + 1, player, playerColor)
        } else {
            ansiGridStringBuilder.setCell(left + 2, top + 1, emptySpace, color)
        }

        when {
            room.hasLockedExitInDirection(Direction.DOWN) -> {
                ansiGridStringBuilder.setCell(left + 3, top + 1, lockedExit, lockedExitColor)
            }
            room.hasUnlockedExitInDirection(Direction.DOWN) -> {
                ansiGridStringBuilder.setCell(left + 3, top + 1, down, color)
            }
            else -> {
                ansiGridStringBuilder.setCell(left + 3, top + 1, emptySpace, color)
            }
        }

        when {
            room.hasLockedExitInDirection(Direction.EAST) -> {
                ansiGridStringBuilder.setCell(left + 4, top + 1, lockedExit, lockedExitColor)
            }
            room.hasUnlockedExitInDirection(Direction.EAST) -> {
                ansiGridStringBuilder.setCell(left + 4, top + 1, unlockedExit, color)
            }
            else -> {
                ansiGridStringBuilder.setCell(left + 4, top + 1, verticalBoundary, color)
            }
        }

        ansiGridStringBuilder.setCell(left, top + 2, verticalBoundary, color)

        when {
            room.hasLockedExitInDirection(Direction.SOUTH) -> {
                ansiGridStringBuilder.setCell(left + 1, top + 2, lockedExit, lockedExitColor)
                ansiGridStringBuilder.setCell(left + 2, top + 2, lockedExit, lockedExitColor)
                ansiGridStringBuilder.setCell(left + 3, top + 2, lockedExit, lockedExitColor)
            }
            room.hasUnlockedExitInDirection(Direction.SOUTH) -> {
                ansiGridStringBuilder.setCell(left + 1, top + 2, unlockedExit, color)
                ansiGridStringBuilder.setCell(left + 2, top + 2, unlockedExit, color)
                ansiGridStringBuilder.setCell(left + 3, top + 2, unlockedExit, color)
            }
            else -> {
                ansiGridStringBuilder.setCell(left + 1, top + 2, horizontalBoundary, color)
                ansiGridStringBuilder.setCell(left + 2, top + 2, horizontalBoundary, color)
                ansiGridStringBuilder.setCell(left + 3, top + 2, horizontalBoundary, color)
            }
        }

        ansiGridStringBuilder.setCell(left + 4, top + 2, verticalBoundary, color)
    }

    private fun drawLowerFloorRoom(
        left: Int,
        top: Int,
        ansiGridStringBuilder: AnsiGridStringBuilder
    ) {
        /*
         * .....
         * .....
         * .....
         */

        for (y in 0 until 3) {
            for (x in 0 until 5) {
                ansiGridStringBuilder.setCell(left + x, top + y, lowerLevel, lowerFloorColor)
            }
        }
    }

    private fun tryConvertMatrixPositionToGridLayoutPosition(
        gridStartX: Int,
        gridStartY: Int,
        gridAvailableWidth: Int,
        gridAvailableHeight: Int,
        matrix: Matrix,
        roomX: Int,
        roomY: Int,
        playerX: Int,
        playerY: Int
    ): FramePosition? {
        val roomWidth = 5
        val roomHeight = 3

        // set position of room, Y is inverted
        var gridLeft: Int = gridStartX + (roomX * roomWidth)
        var gridTop: Int = gridStartY + ((matrix.height - 1) * roomHeight) - (roomY * roomHeight)

        // check if map will fit
        if (matrix.width * roomWidth > gridAvailableWidth || matrix.height * roomHeight > gridAvailableHeight) {
            // centralise on player
            gridLeft += (gridAvailableWidth / 2) - (playerX * roomWidth) + (roomWidth / 2)
            gridTop += (gridAvailableHeight / 2) + ((playerY - matrix.height) * roomHeight) - (roomHeight / 2)
        } else {
            // centralise on area
            gridLeft += kotlin.math.floor((gridAvailableWidth / 2.0) - ((matrix.width / 2.0) * roomWidth)).toInt()
            gridTop += kotlin.math.floor((gridAvailableHeight / 2.0) - ((matrix.height / 2.0) * roomHeight)).toInt()
        }

        val valid = gridLeft >= gridStartX &&
            gridLeft + roomWidth - 1 < gridAvailableWidth &&
            gridTop >= gridStartY &&
            gridTop + roomHeight - 1 < gridAvailableHeight

        return if (valid) {
            FramePosition(gridLeft, gridTop)
        } else {
            null
        }
    }

    /**
     * Build a map of a [Region] on a [ansiGridStringBuilder] with a [region] and a [viewPoint], with a [width] and
     * [height]. Return the end [FramePosition].
     */
    public fun build(
        ansiGridStringBuilder: AnsiGridStringBuilder,
        region: Region,
        x: Int,
        y: Int,
        maxWidth: Int,
        maxHeight: Int
    ) {
        val matrix = region.toMatrix()
        val currentRoom: Room = region.currentRoom ?: return
        val currentRoomPosition = region.getPositionOfRoom(currentRoom) ?: return
        val currentFloor = currentRoomPosition.z
        val rooms = matrix.toRoomPositions().map { it.room }
        val unvisitedRoomPositions = rooms.map { region.getPositionOfRoom(it) }
            .filter { it?.room?.hasBeenVisited == false }
            .toList()
        val visitedRoomPositions = rooms.map { region.getPositionOfRoom(it) }
            .filter { it?.room?.hasBeenVisited == true }
            .toList()
        val multiLevel = matrix.depth > 1
        val indicatorLength = 3 + matrix.depth.toString().length
        var currentX = x
        var currentY = y
        var currentMaxWidth = maxWidth

        if (multiLevel) {
            // draw floor indicators
            for (floor in matrix.depth - 1 downTo 0) {
                val roomsOnThisFloor = rooms.filter { region.getPositionOfRoom(it)?.z == floor }

                // only draw levels indicators where a region is visible without discovery or a room on the floor has
                // been visited
                if (!region.visibleWithoutDiscovery && roomsOnThisFloor.all { !it.hasBeenVisited }) continue

                if (floor == currentFloor) {
                    ansiGridStringBuilder.drawWrapped(
                        "$currentFloorIndicator L$floor",
                        currentX,
                        ++currentY,
                        currentMaxWidth,
                        visitedBoundaryColor
                    )
                } else {
                    ansiGridStringBuilder.drawWrapped(
                        "L$floor",
                        currentX + 2,
                        ++currentY,
                        currentMaxWidth,
                        lowerFloorColor
                    )
                }
            }

            currentX += indicatorLength
            currentMaxWidth -= indicatorLength
        }

        // firstly draw lower levels
        if (showLowerFloors) {
            val lowerLevelRooms = mutableListOf<RoomPosition>()
            lowerLevelRooms.addAll(visitedRoomPositions.filterNotNull().filter { it.z < currentFloor })

            if (region.visibleWithoutDiscovery) {
                lowerLevelRooms.addAll(unvisitedRoomPositions.filterNotNull().filter { it.z < currentFloor })
            }

            for (position in lowerLevelRooms) {
                val gridPosition = tryConvertMatrixPositionToGridLayoutPosition(
                    currentX,
                    currentY,
                    currentMaxWidth,
                    maxHeight,
                    matrix,
                    position.x,
                    position.y,
                    currentRoomPosition.x,
                    currentRoomPosition.y
                )

                if (gridPosition != null) {
                    drawLowerFloorRoom(gridPosition.x, gridPosition.y, ansiGridStringBuilder)
                }
            }
        }

        // now current level
        val currentLevelRooms = mutableListOf<RoomPosition>()
        currentLevelRooms.addAll(visitedRoomPositions.filterNotNull().filter { it.z == currentFloor })

        if (region.visibleWithoutDiscovery) {
            currentLevelRooms.addAll(unvisitedRoomPositions.filterNotNull().filter { it.z == currentFloor })
        }

        for (position in currentLevelRooms) {
            val gridPosition = tryConvertMatrixPositionToGridLayoutPosition(
                currentX,
                currentY,
                currentMaxWidth,
                maxHeight,
                matrix,
                position.x,
                position.y,
                currentRoomPosition.x,
                currentRoomPosition.y
            )

            if (gridPosition != null) {
                drawCurrentFloorRoom(
                    position.room,
                    gridPosition.x,
                    gridPosition.y,
                    position.room == region.currentRoom,
                    ansiGridStringBuilder
                )
            }
        }
    }
}
