package com.github.benpollarduk.ktaf.rendering.frames

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Matrix
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.RoomPosition
import com.github.benpollarduk.ktaf.rendering.FramePosition

/**
 * Provides a region map builder.
 */
@Suppress("LongMethod", "CyclomaticComplexMethod")
public class GridRegionMapBuilder(
    private val lockedExit: Char = 'x',
    private val unlockedExit: Char = ' ',
    private val emptySpace: Char = ' ',
    private val verticalBoundary: Char = '|',
    private val horizontalBoundary: Char = '-',
    private val lowerLevel: Char = '.',
    private val player: Char = 'O',
    private val currentFloorIndicator: Char = '*',
    private val showLowerFloors: Boolean = true
) {
    private fun drawCurrentFloorRoom(
        room: Room,
        left: Int,
        top: Int,
        isCurrent: Boolean,
        gridStringBuilder: GridStringBuilder
    ) {
        /*
         * |   |
         *  ^Ov|
         * |---|
         */

        gridStringBuilder.setCell(left, top, verticalBoundary)

        when {
            room.hasLockedExitInDirection(Direction.NORTH) -> {
                gridStringBuilder.setCell(left + 1, top, lockedExit)
                gridStringBuilder.setCell(left + 2, top, lockedExit)
                gridStringBuilder.setCell(left + 3, top, lockedExit)
            }
            room.hasUnlockedExitInDirection(Direction.NORTH) -> {
                gridStringBuilder.setCell(left + 2, top, unlockedExit)
                gridStringBuilder.setCell(left + 3, top, unlockedExit)
                gridStringBuilder.setCell(left + 1, top, unlockedExit)
            }
            else -> {
                gridStringBuilder.setCell(left + 1, top, horizontalBoundary)
                gridStringBuilder.setCell(left + 2, top, horizontalBoundary)
                gridStringBuilder.setCell(left + 3, top, horizontalBoundary)
            }
        }

        gridStringBuilder.setCell(left + 4, top, verticalBoundary)

        when {
            room.hasLockedExitInDirection(Direction.WEST) -> {
                gridStringBuilder.setCell(left, top + 1, lockedExit)
            }
            room.hasUnlockedExitInDirection(Direction.WEST) -> {
                gridStringBuilder.setCell(left, top + 1, unlockedExit)
            }
            else -> {
                gridStringBuilder.setCell(left, top + 1, verticalBoundary)
            }
        }

        when {
            room.hasLockedExitInDirection(Direction.UP) -> {
                gridStringBuilder.setCell(left + 1, top + 1, lockedExit)
            }
            room.hasUnlockedExitInDirection(Direction.UP) -> {
                gridStringBuilder.setCell(left + 1, top + 1, '^')
            }
            else -> {
                gridStringBuilder.setCell(left + 1, top + 1, emptySpace)
            }
        }

        if (isCurrent) {
            gridStringBuilder.setCell(left + 2, top + 1, player)
        } else {
            gridStringBuilder.setCell(left + 2, top + 1, emptySpace)
        }

        when {
            room.hasLockedExitInDirection(Direction.DOWN) -> {
                gridStringBuilder.setCell(left + 3, top + 1, lockedExit)
            }
            room.hasUnlockedExitInDirection(Direction.DOWN) -> {
                gridStringBuilder.setCell(left + 3, top + 1, 'v')
            }
            else -> {
                gridStringBuilder.setCell(left + 3, top + 1, emptySpace)
            }
        }

        when {
            room.hasLockedExitInDirection(Direction.EAST) -> {
                gridStringBuilder.setCell(left + 4, top + 1, lockedExit)
            }
            room.hasUnlockedExitInDirection(Direction.EAST) -> {
                gridStringBuilder.setCell(left + 4, top + 1, unlockedExit)
            }
            else -> {
                gridStringBuilder.setCell(left + 4, top + 1, verticalBoundary)
            }
        }

        gridStringBuilder.setCell(left, top + 2, verticalBoundary)

        when {
            room.hasLockedExitInDirection(Direction.SOUTH) -> {
                gridStringBuilder.setCell(left + 1, top + 2, lockedExit)
                gridStringBuilder.setCell(left + 2, top + 2, lockedExit)
                gridStringBuilder.setCell(left + 3, top + 2, lockedExit)
            }
            room.hasUnlockedExitInDirection(Direction.SOUTH) -> {
                gridStringBuilder.setCell(left + 1, top + 2, unlockedExit)
                gridStringBuilder.setCell(left + 2, top + 2, unlockedExit)
                gridStringBuilder.setCell(left + 3, top + 2, unlockedExit)
            }
            else -> {
                gridStringBuilder.setCell(left + 1, top + 2, horizontalBoundary)
                gridStringBuilder.setCell(left + 2, top + 2, horizontalBoundary)
                gridStringBuilder.setCell(left + 3, top + 2, horizontalBoundary)
            }
        }

        gridStringBuilder.setCell(left + 4, top + 2, verticalBoundary)
    }

    private fun drawLowerFloorRoom(
        left: Int,
        top: Int,
        gridStringBuilder: GridStringBuilder
    ) {
        /*
         * .....
         * .....
         * .....
         *
         */

        for (y in 0 until 3) {
            for (x in 0 until 5) {
                gridStringBuilder.setCell(left + x, top + y, lowerLevel)
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
     * Build a map of a [Region] on a [gridStringBuilder] with a [region] and a [viewPoint], with a [width] and
     * [height]. Return the end [FramePosition].
     */
    public fun build(
        gridStringBuilder: GridStringBuilder,
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
        val rooms = matrix.toRooms()
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
                    gridStringBuilder.drawWrapped(
                        "$currentFloorIndicator L$floor",
                        currentX,
                        ++currentY,
                        currentMaxWidth
                    )
                } else {
                    gridStringBuilder.drawWrapped(
                        "L$floor",
                        currentX + 2,
                        ++currentY,
                        currentMaxWidth
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
                    drawLowerFloorRoom(gridPosition.x, gridPosition.y, gridStringBuilder)
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
                    gridStringBuilder
                )
            }
        }
    }
}
