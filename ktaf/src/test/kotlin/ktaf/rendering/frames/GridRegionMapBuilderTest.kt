package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.rendering.frames.GridRegionMapBuilder
import ktaf.rendering.frames.GridStringBuilder
import ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GridRegionMapBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = GridRegionMapBuilder()
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 0, 0] = Room("", "", listOf(Exit(Direction.EAST)))
        regionMaker[1, 0, 0] = Room("", "", listOf(Exit(Direction.EAST, true), Exit(Direction.WEST)))
        regionMaker[2, 0, 0] = Room("", "", listOf(Exit(Direction.WEST, true)))
        val region = regionMaker.make()
        val gridStringBuilder = GridStringBuilder().also {
            it.resize(Size(80, 50))
        }

        // When
        val result = builder.build(gridStringBuilder, region, 0, 0, 80, 50).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}