package ktaf.rendering.frames.html

import ktaf.assets.locations.Region
import ktaf.extensions.removeWhitespaceLines
import ktaf.helpers.newline
import ktaf.logic.Game
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.GridRegionMapBuilder
import ktaf.rendering.frames.GridStringBuilder
import ktaf.rendering.frames.RegionMapFrameBuilder

/**
 * Provides an HTML region map frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HTMLRegionMapFrameBuilder(
    private val htmlPageBuilder: HTMLPageBuilder,
    private val regionMapBuilder: GridRegionMapBuilder
) : RegionMapFrameBuilder {
    override fun build(region: Region, game: Game): Frame {
        htmlPageBuilder.h1(region.identifier.name)
        htmlPageBuilder.br()

        val gridStringBuilder = GridStringBuilder().also {
            it.resize(game.frameSize)
        }
        regionMapBuilder.build(gridStringBuilder, region, 0, 0, game.frameSize.width, game.frameSize.height)

        var map = gridStringBuilder.toString().removeWhitespaceLines()
        htmlPageBuilder.pre(map.replace(newline(), "<br>"))

        return HTMLFrame(htmlPageBuilder).also {
            it.acceptsInput = false
        }
    }
}
