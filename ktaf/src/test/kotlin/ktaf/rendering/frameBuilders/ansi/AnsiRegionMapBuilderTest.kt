package ktaf.rendering.frameBuilders.ansi

import ktaf.assets.Size
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import ktaf.rendering.frames.ansi.AnsiGridTextFrame
import ktaf.rendering.frames.ansi.AnsiRegionMapBuilder
import ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiRegionMapBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiRegionMapBuilder()
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 0, 0] = Room("", "", listOf(Exit(Direction.EAST)))
        regionMaker[1, 0, 0] = Room("", "", listOf(Exit(Direction.EAST, true), Exit(Direction.WEST)))
        regionMaker[2, 0, 0] = Room("", "", listOf(Exit(Direction.WEST, true)))
        val region = regionMaker.make()
        val gridStringBuilder = AnsiGridStringBuilder().also {
            it.resize(Size(80, 50))
        }

        // When
        builder.build(gridStringBuilder, region, 0, 0, 80, 50).toString()
        val result = AnsiGridTextFrame(gridStringBuilder, 0, 0).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
