package ktaf.rendering.frames

import ktaf.assets.locations.Region
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder

/**
 * Provides a standard interface for building [Region] maps.
 */
public interface RegionMapBuilder {
    /**
     * Build a map of a [Region] on a [ansiGridStringBuilder] with a [region] and a [viewPoint], with a [width] and [height]. Return the end
     * [FramePosition].
     */
    public fun build(
        ansiGridStringBuilder: AnsiGridStringBuilder,
        region: Region,
        x: Int,
        y: Int,
        maxWidth: Int,
        maxHeight: Int
    )
}
