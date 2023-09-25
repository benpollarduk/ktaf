package ktaf.rendering.frames

import ktaf.assets.locations.Room
import ktaf.assets.locations.ViewPoint
import ktaf.rendering.FramePosition
import ktaf.rendering.KeyType
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder

/**
 * Provides a standard interface for building [Room] maps.
 */
public interface RoomMapBuilder {
    /**
     * Build a map of a [Room] on a [ansiGridStringBuilder] with a [room] and a [viewPoint], with a [startX] and [startY]
     * position. Return the end [FramePosition].
     */
    public fun build(
        ansiGridStringBuilder: AnsiGridStringBuilder,
        room: Room,
        viewPoint: ViewPoint,
        keyType: KeyType,
        startX: Int,
        startY: Int
    ): FramePosition
}
