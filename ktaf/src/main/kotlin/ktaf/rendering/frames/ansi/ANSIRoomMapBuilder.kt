package ktaf.rendering.frames.ansi

import ktaf.assets.locations.Direction
import ktaf.assets.locations.Room
import ktaf.assets.locations.ViewPoint
import ktaf.rendering.FramePosition
import ktaf.rendering.KeyType

/**
 * Provides an ANSI [Room] map builder.
 */
public class ANSIRoomMapBuilder(
    private val lockedExit: Char = 'x',
    private val itemOrCharacterInRoom: Char = '?',
    private val verticalBoundary: Char = '|',
    private val horizontalBoundary: Char = '-',
    private val verticalExitBorder: Char = '|',
    private val horizontalExitBorder: Char = '-',
    private val corner: Char = '+',
    private val keyPadding: Int = 6,
    private val boundaryColor: ANSIColor = ANSIColor.BRIGHT_BLACK,
    private val itemOrCharacterColor: ANSIColor = ANSIColor.BLUE,
    private val lockedExitColor: ANSIColor = ANSIColor.RED,
    private val visitedExitColor: ANSIColor = ANSIColor.YELLOW,
    private val unvisitedExitColor: ANSIColor = ANSIColor.GREEN
) {
    private fun drawNorthBorder(
        room: Room,
        viewPoint: ViewPoint,
        ansiGridStringBuilder: ANSIGridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        ansiGridStringBuilder.setCell(startX, startY, corner, boundaryColor)
        ansiGridStringBuilder.setCell(startX + 1, startY, horizontalBoundary, boundaryColor)

        when {
            room.hasLockedExitInDirection(Direction.NORTH) -> {
                ansiGridStringBuilder.setCell(startX + 2, startY, verticalExitBorder, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 4, startY, lockedExit, lockedExitColor)
                ansiGridStringBuilder.setCell(startX + 6, startY, verticalExitBorder, boundaryColor)
            }
            room.hasUnlockedExitInDirection(Direction.NORTH) -> {
                ansiGridStringBuilder.setCell(startX + 2, startY, verticalExitBorder, boundaryColor)

                if (viewPoint[Direction.NORTH]?.hasBeenVisited == true) {
                    ansiGridStringBuilder.setCell(startX + 4, startY, 'n', visitedExitColor)
                } else {
                    ansiGridStringBuilder.setCell(startX + 4, startY, 'N', unvisitedExitColor)
                }
                ansiGridStringBuilder.setCell(startX + 6, startY, verticalExitBorder, boundaryColor)
            }
            else -> {
                ansiGridStringBuilder.setCell(startX + 2, startY, horizontalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 3, startY, horizontalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 4, startY, horizontalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 5, startY, horizontalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 6, startY, horizontalBoundary, boundaryColor)
            }
        }

        ansiGridStringBuilder.setCell(startX + 7, startY, horizontalBoundary, boundaryColor)
        ansiGridStringBuilder.setCell(startX + 8, startY, corner, boundaryColor)
    }

    private fun drawSouthBorder(
        room: Room,
        viewPoint: ViewPoint,
        ansiGridStringBuilder: ANSIGridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        ansiGridStringBuilder.setCell(startX, startY + 6, corner, boundaryColor)
        ansiGridStringBuilder.setCell(startX + 1, startY + 6, horizontalBoundary, boundaryColor)

        when {
            room.hasLockedExitInDirection(Direction.SOUTH) -> {
                ansiGridStringBuilder.setCell(startX + 2, startY + 6, verticalExitBorder, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 4, startY + 6, lockedExit, lockedExitColor)
                ansiGridStringBuilder.setCell(startX + 6, startY + 6, verticalExitBorder, boundaryColor)
            }
            room.hasUnlockedExitInDirection(Direction.SOUTH) -> {
                ansiGridStringBuilder.setCell(startX + 2, startY + 6, verticalExitBorder, boundaryColor)

                if (viewPoint[Direction.SOUTH]?.hasBeenVisited == true) {
                    ansiGridStringBuilder.setCell(startX + 4, startY + 6, 's', visitedExitColor)
                } else {
                    ansiGridStringBuilder.setCell(startX + 4, startY + 6, 'S', unvisitedExitColor)
                }
                ansiGridStringBuilder.setCell(startX + 6, startY + 6, verticalExitBorder, boundaryColor)
            }
            else -> {
                ansiGridStringBuilder.setCell(startX + 2, startY + 6, horizontalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 3, startY + 6, horizontalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 4, startY + 6, horizontalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 5, startY + 6, horizontalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 6, startY + 6, horizontalBoundary, boundaryColor)
            }
        }

        ansiGridStringBuilder.setCell(startX + 7, startY + 6, horizontalBoundary, boundaryColor)
        ansiGridStringBuilder.setCell(startX + 8, startY + 6, corner, boundaryColor)
    }

    private fun drawEastBorder(
        room: Room,
        viewPoint: ViewPoint,
        ansiGridStringBuilder: ANSIGridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        ansiGridStringBuilder.setCell(startX + 8, startY + 1, verticalBoundary, boundaryColor)

        when {
            room.hasLockedExitInDirection(Direction.EAST) -> {
                ansiGridStringBuilder.setCell(startX + 8, startY + 2, horizontalExitBorder, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 8, startY + 3, lockedExit, lockedExitColor)
                ansiGridStringBuilder.setCell(startX + 8, startY + 4, horizontalExitBorder, boundaryColor)
            }
            room.hasUnlockedExitInDirection(Direction.EAST) -> {
                ansiGridStringBuilder.setCell(startX + 8, startY + 2, horizontalExitBorder, boundaryColor)

                if (viewPoint[Direction.EAST]?.hasBeenVisited == true) {
                    ansiGridStringBuilder.setCell(startX + 8, startY + 3, 'e', visitedExitColor)
                } else {
                    ansiGridStringBuilder.setCell(startX + 8, startY + 3, 'E', unvisitedExitColor)
                }

                ansiGridStringBuilder.setCell(startX + 8, startY + 4, horizontalExitBorder, boundaryColor)
            }
            else -> {
                ansiGridStringBuilder.setCell(startX + 8, startY + 2, verticalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 8, startY + 3, verticalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX + 8, startY + 4, verticalBoundary, boundaryColor)
            }
        }

        ansiGridStringBuilder.setCell(startX + 8, startY + 5, verticalExitBorder, boundaryColor)
    }

    private fun drawWestBorder(
        room: Room,
        viewPoint: ViewPoint,
        ansiGridStringBuilder: ANSIGridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        ansiGridStringBuilder.setCell(startX, startY + 1, verticalBoundary, boundaryColor)

        when {
            room.hasLockedExitInDirection(Direction.WEST) -> {
                ansiGridStringBuilder.setCell(startX, startY + 2, horizontalExitBorder, boundaryColor)
                ansiGridStringBuilder.setCell(startX, startY + 3, lockedExit, lockedExitColor)
                ansiGridStringBuilder.setCell(startX, startY + 4, horizontalExitBorder, boundaryColor)
            }
            room.hasUnlockedExitInDirection(Direction.WEST) -> {
                ansiGridStringBuilder.setCell(startX, startY + 2, horizontalExitBorder, boundaryColor)

                if (viewPoint[Direction.WEST]?.hasBeenVisited == true) {
                    ansiGridStringBuilder.setCell(startX, startY + 3, 'w', visitedExitColor)
                } else {
                    ansiGridStringBuilder.setCell(startX, startY + 3, 'W', unvisitedExitColor)
                }

                ansiGridStringBuilder.setCell(startX, startY + 4, horizontalExitBorder, boundaryColor)
            }
            else -> {
                ansiGridStringBuilder.setCell(startX, startY + 2, verticalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX, startY + 3, verticalBoundary, boundaryColor)
                ansiGridStringBuilder.setCell(startX, startY + 4, verticalBoundary, boundaryColor)
            }
        }

        ansiGridStringBuilder.setCell(startX, startY + 5, verticalExitBorder, boundaryColor)
    }

    private fun drawUpExit(
        room: Room,
        viewPoint: ViewPoint,
        ansiGridStringBuilder: ANSIGridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        if (room.hasLockedExitInDirection(Direction.UP)) {
            ansiGridStringBuilder.setCell(startX + 2, startY + 2, lockedExit, lockedExitColor)
        } else if (room.hasUnlockedExitInDirection(Direction.UP)) {
            if (viewPoint[Direction.UP]?.hasBeenVisited == true) {
                ansiGridStringBuilder.setCell(startX + 2, startY + 2, 'u', visitedExitColor)
            } else {
                ansiGridStringBuilder.setCell(startX + 2, startY + 2, 'U', unvisitedExitColor)
            }
        }
    }

    private fun drawDownExit(
        room: Room,
        viewPoint: ViewPoint,
        ansiGridStringBuilder: ANSIGridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        if (room.hasLockedExitInDirection(Direction.DOWN)) {
            ansiGridStringBuilder.setCell(startX + 6, startY + 2, lockedExit, lockedExitColor)
        } else if (room.hasUnlockedExitInDirection(Direction.DOWN)) {
            if (viewPoint[Direction.DOWN]?.hasBeenVisited == true) {
                ansiGridStringBuilder.setCell(startX + 6, startY + 2, 'd', visitedExitColor)
            } else {
                ansiGridStringBuilder.setCell(startX + 6, startY + 2, 'D', unvisitedExitColor)
            }
        }
    }

    private fun drawItemOrCharacter(
        room: Room,
        ansiGridStringBuilder: ANSIGridStringBuilder,
        startX: Int,
        startY: Int
    ) {
        if (room.items.any { it.isPlayerVisible } || room.characters.any { it.isPlayerVisible }) {
            ansiGridStringBuilder.setCell(startX + 4, startY + 3, itemOrCharacterInRoom, itemOrCharacterColor)
        }
    }

    @Suppress("ReplacePutWithAssignment")
    private fun drawKey(
        room: Room,
        viewPoint: ViewPoint,
        keyType: KeyType,
        ansiGridStringBuilder: ANSIGridStringBuilder,
        startX: Int,
        startY: Int
    ): FramePosition {
        val keyLines = mutableMapOf<String, ANSIColor>()
        val lockedExitString = "$lockedExit = Locked Exit"
        val notVisitedString = "N/E/S/W/U/D = Unvisited"
        val visitedString = "n/s/e/w/u/d = Visited"
        val itemsString = "$itemOrCharacterInRoom = Item(s) or Character(s) in Room"

        when (keyType) {
            KeyType.DYNAMIC -> {
                if (room.exits.any { it.isPlayerVisible && it.isLocked }) {
                    keyLines.put(lockedExitString, lockedExitColor)
                }

                if (viewPoint.anyNotVisited) {
                    keyLines.put(notVisitedString, unvisitedExitColor)
                }

                if (viewPoint.anyVisited) {
                    keyLines.put(visitedString, visitedExitColor)
                }

                val enteredFrom = room.enteredFrom

                if (enteredFrom != null) {
                    keyLines.put("${enteredFrom.toString().lowercase().first()} = Entrance", visitedExitColor)
                }

                if (room.items.any { it.isPlayerVisible } || room.characters.any { it.isPlayerVisible }) {
                    keyLines.put(itemsString, itemOrCharacterColor)
                }
            }
            KeyType.FULL -> {
                keyLines.put(lockedExitString, lockedExitColor)
                keyLines.put(notVisitedString, unvisitedExitColor)
                keyLines.put(visitedString, visitedExitColor)
                keyLines.put(itemsString, itemOrCharacterColor)
            }
            KeyType.NONE -> {
                // nothing
            }
        }

        var endPosition = FramePosition(startX + 8, startY)

        if (keyLines.any()) {
            val startKeyX = endPosition.x + keyPadding
            val maxWidth = keyLines.maxOf { it.key.length } + startKeyX + 1

            keyLines.forEach {
                endPosition = ansiGridStringBuilder.drawWrapped(
                    it.key,
                    startKeyX,
                    endPosition.y + 1,
                    maxWidth,
                    it.value
                )
            }
        }

        return endPosition
    }

    /**
     * Build a map of a [Room] on a [ansiGridStringBuilder] with a [room] and a [viewPoint], with a [startX] and [startY]
     * position. Return the end [FramePosition].
     */
    public fun build(
        ansiGridStringBuilder: ANSIGridStringBuilder,
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

        drawNorthBorder(room, viewPoint, ansiGridStringBuilder, startX, startY)
        drawSouthBorder(room, viewPoint, ansiGridStringBuilder, startX, startY)
        drawEastBorder(room, viewPoint, ansiGridStringBuilder, startX, startY)
        drawWestBorder(room, viewPoint, ansiGridStringBuilder, startX, startY)
        drawUpExit(room, viewPoint, ansiGridStringBuilder, startX, startY)
        drawDownExit(room, viewPoint, ansiGridStringBuilder, startX, startY)
        drawItemOrCharacter(room, ansiGridStringBuilder, startX, startY)
        var endPosition = drawKey(room, viewPoint, keyType, ansiGridStringBuilder, startX, startY)

        if (endPosition.y < startY + 6) {
            endPosition = FramePosition(endPosition.x, startY + 6)
        }

        return endPosition
    }
}
