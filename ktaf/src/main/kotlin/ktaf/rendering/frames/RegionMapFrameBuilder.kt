package ktaf.rendering.frames

import ktaf.assets.locations.Region

/**
 * Provides a standard interface for region map frames.
 */
public interface RegionMapFrameBuilder {
    /**
     * Build a frame with a [region].
     */
    public fun build(region: Region): Frame
}
