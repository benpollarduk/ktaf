package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.rendering.frames.Frame
import com.github.benpollarduk.ktaf.rendering.frames.GridRegionMapBuilder
import com.github.benpollarduk.ktaf.rendering.frames.GridStringBuilder
import com.github.benpollarduk.ktaf.rendering.frames.RegionMapFrameBuilder
import com.github.benpollarduk.ktaf.utilities.NEWLINE

/**
 * Provides an HTML region map frame builder that builds in to the specified [htmlPageBuilder].
 */
public class HtmlRegionMapFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder,
    private val regionMapBuilder: GridRegionMapBuilder,
    private val frameSize: Size
) : RegionMapFrameBuilder {
    override fun build(region: Region): Frame {
        htmlPageBuilder.h1(region.identifier.name)
        htmlPageBuilder.br()

        val gridStringBuilder = GridStringBuilder().also {
            it.resize(frameSize)
        }
        regionMapBuilder.build(gridStringBuilder, region, 0, 0, frameSize.width, frameSize.height)

        var map = gridStringBuilder.toString()
        htmlPageBuilder.pre(map.replace(NEWLINE, "<br>"))

        return HtmlFrame(htmlPageBuilder, false)
    }
}
