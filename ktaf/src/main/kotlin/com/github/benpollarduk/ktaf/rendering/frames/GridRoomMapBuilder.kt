package com.github.benpollarduk.ktaf.rendering.frames

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.ViewPoint
import com.github.benpollarduk.ktaf.rendering.FramePosition
import com.github.benpollarduk.ktaf.rendering.KeyType

/**
 * Provides a [Room] map builder.
 */
public class GridRoomMapBuilder(
    private val lockedExit: Char = 'x',
    private val itemOrCharacterInRoom: Char = '?',
    private val verticalBoundary: Char = '|',
    private val horizontalBoundary: Char = '-',
    private val verticalExitBorder: Char = '|',
    private val horizontalExitBorder: Char = '-',
    private val corner: Char = '+',
    private val keyPadding: Int = 6
) {
    private fun drawNorthBorder(
        room: Room,
        viewPoint: ViewPoint,
        gridStringBuilder: GridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        gridStringBuilder.setCell(startX, startY, corner)
        gridStringBuilder.setCell(startX + 1, startY, horizontalBoundary)

        when {
            room.hasLockedExitInDirection(Direction.NORTH) -> {
                gridStringBuilder.setCell(startX + 2, startY, verticalExitBorder)
                gridStringBuilder.setCell(startX + 4, startY, lockedExit)
                gridStringBuilder.setCell(startX + 6, startY, verticalExitBorder)
            }
            room.hasUnlockedExitInDirection(Direction.NORTH) -> {
                gridStringBuilder.setCell(startX + 2, startY, verticalExitBorder)

                if (viewPoint[Direction.NORTH]?.hasBeenVisited == true) {
                    gridStringBuilder.setCell(startX + 4, startY, 'n')
                } else {
                    gridStringBuilder.setCell(startX + 4, startY, 'N')
                }
                gridStringBuilder.setCell(startX + 6, startY, verticalExitBorder)
            }
            else -> {
                gridStringBuilder.setCell(startX + 2, startY, horizontalBoundary)
                gridStringBuilder.setCell(startX + 3, startY, horizontalBoundary)
                gridStringBuilder.setCell(startX + 4, startY, horizontalBoundary)
                gridStringBuilder.setCell(startX + 5, startY, horizontalBoundary)
                gridStringBuilder.setCell(startX + 6, startY, horizontalBoundary)
            }
        }

        gridStringBuilder.setCell(startX + 7, startY, horizontalBoundary)
        gridStringBuilder.setCell(startX + 8, startY, corner)
    }

    private fun drawSouthBorder(
        room: Room,
        viewPoint: ViewPoint,
        gridStringBuilder: GridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        gridStringBuilder.setCell(startX, startY + 6, corner)
        gridStringBuilder.setCell(startX + 1, startY + 6, horizontalBoundary)

        when {
            room.hasLockedExitInDirection(Direction.SOUTH) -> {
                gridStringBuilder.setCell(startX + 2, startY + 6, verticalExitBorder)
                gridStringBuilder.setCell(startX + 4, startY + 6, lockedExit)
                gridStringBuilder.setCell(startX + 6, startY + 6, verticalExitBorder)
            }
            room.hasUnlockedExitInDirection(Direction.SOUTH) -> {
                gridStringBuilder.setCell(startX + 2, startY + 6, verticalExitBorder)

                if (viewPoint[Direction.SOUTH]?.hasBeenVisited == true) {
                    gridStringBuilder.setCell(startX + 4, startY + 6, 's')
                } else {
                    gridStringBuilder.setCell(startX + 4, startY + 6, 'S')
                }
                gridStringBuilder.setCell(startX + 6, startY + 6, verticalExitBorder)
            }
            else -> {
                gridStringBuilder.setCell(startX + 2, startY + 6, horizontalBoundary)
                gridStringBuilder.setCell(startX + 3, startY + 6, horizontalBoundary)
                gridStringBuilder.setCell(startX + 4, startY + 6, horizontalBoundary)
                gridStringBuilder.setCell(startX + 5, startY + 6, horizontalBoundary)
                gridStringBuilder.setCell(startX + 6, startY + 6, horizontalBoundary)
            }
        }

        gridStringBuilder.setCell(startX + 7, startY + 6, horizontalBoundary)
        gridStringBuilder.setCell(startX + 8, startY + 6, corner)
    }

    private fun drawEastBorder(
        room: Room,
        viewPoint: ViewPoint,
        gridStringBuilder: GridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        gridStringBuilder.setCell(startX + 8, startY + 1, verticalBoundary)

        when {
            room.hasLockedExitInDirection(Direction.EAST) -> {
                gridStringBuilder.setCell(startX + 8, startY + 2, horizontalExitBorder)
                gridStringBuilder.setCell(startX + 8, startY + 3, lockedExit)
                gridStringBuilder.setCell(startX + 8, startY + 4, horizontalExitBorder)
            }
            room.hasUnlockedExitInDirection(Direction.EAST) -> {
                gridStringBuilder.setCell(startX + 8, startY + 2, horizontalExitBorder)

                if (viewPoint[Direction.EAST]?.hasBeenVisited == true) {
                    gridStringBuilder.setCell(startX + 8, startY + 3, 'e')
                } else {
                    gridStringBuilder.setCell(startX + 8, startY + 3, 'E')
                }

                gridStringBuilder.setCell(startX + 8, startY + 4, horizontalExitBorder)
            }
            else -> {
                gridStringBuilder.setCell(startX + 8, startY + 2, verticalBoundary)
                gridStringBuilder.setCell(startX + 8, startY + 3, verticalBoundary)
                gridStringBuilder.setCell(startX + 8, startY + 4, verticalBoundary)
            }
        }

        gridStringBuilder.setCell(startX + 8, startY + 5, verticalExitBorder)
    }

    private fun drawWestBorder(
        room: Room,
        viewPoint: ViewPoint,
        gridStringBuilder: GridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        gridStringBuilder.setCell(startX, startY + 1, verticalBoundary)

        when {
            room.hasLockedExitInDirection(Direction.WEST) -> {
                gridStringBuilder.setCell(startX, startY + 2, horizontalExitBorder)
                gridStringBuilder.setCell(startX, startY + 3, lockedExit)
                gridStringBuilder.setCell(startX, startY + 4, horizontalExitBorder)
            }
            room.hasUnlockedExitInDirection(Direction.WEST) -> {
                gridStringBuilder.setCell(startX, startY + 2, horizontalExitBorder)

                if (viewPoint[Direction.WEST]?.hasBeenVisited == true) {
                    gridStringBuilder.setCell(startX, startY + 3, 'w')
                } else {
                    gridStringBuilder.setCell(startX, startY + 3, 'W')
                }

                gridStringBuilder.setCell(startX, startY + 4, horizontalExitBorder)
            }
            else -> {
                gridStringBuilder.setCell(startX, startY + 2, verticalBoundary)
                gridStringBuilder.setCell(startX, startY + 3, verticalBoundary)
                gridStringBuilder.setCell(startX, startY + 4, verticalBoundary)
            }
        }

        gridStringBuilder.setCell(startX, startY + 5, verticalExitBorder)
    }

    private fun drawUpExit(
        room: Room,
        viewPoint: ViewPoint,
        gridStringBuilder: GridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        if (room.hasLockedExitInDirection(Direction.UP)) {
            gridStringBuilder.setCell(startX + 2, startY + 2, lockedExit)
        } else if (room.hasUnlockedExitInDirection(Direction.UP)) {
            if (viewPoint[Direction.UP]?.hasBeenVisited == true) {
                gridStringBuilder.setCell(startX + 2, startY + 2, 'u')
            } else {
                gridStringBuilder.setCell(startX + 2, startY + 2, 'U')
            }
        }
    }

    private fun drawDownExit(
        room: Room,
        viewPoint: ViewPoint,
        gridStringBuilder: GridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        if (room.hasLockedExitInDirection(Direction.DOWN)) {
            gridStringBuilder.setCell(startX + 6, startY + 2, lockedExit)
        } else if (room.hasUnlockedExitInDirection(Direction.DOWN)) {
            if (viewPoint[Direction.DOWN]?.hasBeenVisited == true) {
                gridStringBuilder.setCell(startX + 6, startY + 2, 'd')
            } else {
                gridStringBuilder.setCell(startX + 6, startY + 2, 'D')
            }
        }
    }

    private fun drawItemOrCharacter(
        room: Room,
        gridStringBuilder: GridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        if (room.items.any { it.isPlayerVisible } || room.characters.any { it.isPlayerVisible }) {
            gridStringBuilder.setCell(startX + 4, startY + 3, itemOrCharacterInRoom)
        }
    }

    @Suppress("ReplacePutWithAssignment")
    private fun drawKey(
        room: Room,
        viewPoint: ViewPoint,
        keyType: KeyType,
        gridStringBuilder: GridStringBuilder,
        startX: Int,
        startY: Int
    ): FramePosition {
        val keyLines = mutableListOf<String>()
        val lockedExitString = "$lockedExit = Locked Exit"
        val notVisitedString = "N/E/S/W/U/D = Unvisited"
        val visitedString = "n/s/e/w/u/d = Visited"
        val itemsString = "$itemOrCharacterInRoom = Item(s) or Character(s) in Room"

        when (keyType) {
            KeyType.DYNAMIC -> {
                if (room.exits.any { it.isPlayerVisible && it.isLocked }) {
                    keyLines.add(lockedExitString)
                }

                if (viewPoint.anyNotVisited) {
                    keyLines.add(notVisitedString)
                }

                if (viewPoint.anyVisited) {
                    keyLines.add(visitedString)
                }

                val enteredFrom = room.enteredFrom

                if (enteredFrom != null) {
                    keyLines.add("${enteredFrom.toString().lowercase().first()} = Entrance")
                }

                if (room.items.any { it.isPlayerVisible } || room.characters.any { it.isPlayerVisible }) {
                    keyLines.add(itemsString)
                }
            }
            KeyType.FULL -> {
                keyLines.add(lockedExitString)
                keyLines.add(notVisitedString)
                keyLines.add(visitedString)
                keyLines.add(itemsString)
            }
            KeyType.NONE -> {
                // nothing
            }
        }

        var endPosition = FramePosition(startX + 8, startY)

        if (keyLines.any()) {
            val startKeyX = endPosition.x + keyPadding
            val maxWidth = keyLines.maxOf { it.length } + startKeyX + 1

            keyLines.forEach {
                endPosition = gridStringBuilder.drawWrapped(it, startKeyX, endPosition.y + 1, maxWidth)
            }
        }

        return endPosition
    }

    /**
     * Build a map of a [Room] on a [gridStringBuilder] with a [room] and a [viewPoint], with a [startX] and [startY]
     * position. Return the end [FramePosition].
     */
    public fun build(
        gridStringBuilder: GridStringBuilder,
        room: Room,
        viewPoint: ViewPoint,
        keyType: KeyType,
        startX: Int,
        startY: Int
    ): FramePosition {
        /*
         * *-| N |-*
         * |       |
         * - U   D -
         * W   ?   E
         * -       -
         * |       |
         * *-| S |-*
         */

        drawNorthBorder(room, viewPoint, gridStringBuilder, startX, startY)
        drawSouthBorder(room, viewPoint, gridStringBuilder, startX, startY)
        drawEastBorder(room, viewPoint, gridStringBuilder, startX, startY)
        drawWestBorder(room, viewPoint, gridStringBuilder, startX, startY)
        drawUpExit(room, viewPoint, gridStringBuilder, startX, startY)
        drawDownExit(room, viewPoint, gridStringBuilder, startX, startY)
        drawItemOrCharacter(room, gridStringBuilder, startX, startY)
        var endPosition = drawKey(room, viewPoint, keyType, gridStringBuilder, startX, startY)

        if (endPosition.y < startY + 6) {
            endPosition = FramePosition(endPosition.x, startY + 6)
        }

        return endPosition
    }
}
