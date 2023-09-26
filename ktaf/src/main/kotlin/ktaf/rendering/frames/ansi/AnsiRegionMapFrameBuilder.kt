package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import ktaf.assets.locations.Region
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.RegionMapFrameBuilder

/**
 * Provides an ANSI region map frame builder that builds in to the specified [ansiGridStringBuilder].
 */
public class AnsiRegionMapFrameBuilder(
    private val ansiGridStringBuilder: AnsiGridStringBuilder,
    private val regionMapBuilder: AnsiRegionMapBuilder,
    private val frameSize: Size,
    private val backgroundColor: AnsiColor = AnsiColor.RESET,
    private val borderColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val titleColor: AnsiColor = AnsiColor.WHITE
) : RegionMapFrameBuilder {
    override fun build(region: Region): Frame {
        val availableWidth = frameSize.width - 4
        val leftMargin = 2

        ansiGridStringBuilder.resize(frameSize)
        ansiGridStringBuilder.drawBoundary(borderColor)
        val lastPosition: FramePosition = ansiGridStringBuilder.drawWrapped(region.identifier.name, leftMargin, 2, availableWidth, titleColor)
        ansiGridStringBuilder.drawUnderline(leftMargin, lastPosition.y + 1, region.identifier.name.length, titleColor)
        regionMapBuilder.build(ansiGridStringBuilder, region, 2, lastPosition.y + 2, availableWidth, frameSize.height - 4)

        return AnsiGridTextFrame(ansiGridStringBuilder, 0, 0, backgroundColor).also {
            it.acceptsInput = false
        }
    }
}
