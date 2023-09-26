package ktaf.rendering.frames.html

import ktaf.assets.Size
import ktaf.assets.locations.Room
import ktaf.rendering.frames.GridRegionMapBuilder
import ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HTMLRegionMapFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HTMLRegionMapFrameBuilder(HTMLPageBuilder(), GridRegionMapBuilder(), Size(80, 50))
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 0, 0] = Room("", "")
        regionMaker[1, 0, 0] = Room("", "")
        regionMaker[2, 0, 0] = Room("", "")
        val region = regionMaker.make()

        // When
        val result = builder.build(region).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
