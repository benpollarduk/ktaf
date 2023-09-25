package ktaf.rendering.frames.html

import ktaf.assets.locations.Region
import ktaf.logic.Game
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.RegionMapFrameBuilder

/**
 * Provides an HTML region map frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HTMLRegionMapFrameBuilder(
    private val htmlPageBuilder: HTMLPageBuilder
) : RegionMapFrameBuilder {
    override fun build(region: Region, game: Game): Frame {
        htmlPageBuilder.h1(region.identifier.name)
        htmlPageBuilder.br()

        // regionMapBuilder.build(0, 0, game.frameSize.width, game.frameSize.height)

        return HTMLFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
