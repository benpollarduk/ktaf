package com.github.benpollarduk.ktaf.rendering.frames

import com.github.benpollarduk.ktaf.assets.locations.Region

/**
 * Provides a standard interface for region map frames.
 */
public fun interface RegionMapFrameBuilder {
    /**
     * Build a frame with a [region].
     */
    public fun build(region: Region): Frame
}
