package ktaf.rendering.frames

import ktaf.assets.locations.Region
import ktaf.logic.Game

/**
 * Provides a standard interface for region map frames.
 */
public interface RegionMapFrameBuilder {
    /**
     * Build a frame with a [region] and [game].
     */
    public fun build(region: Region, game: Game): Frame
}
