package ktaf.rendering.frameBuilders.html

import ktaf.assets.locations.Room
import ktaf.logic.GameTestHelper
import ktaf.rendering.frames.html.HTMLPageBuilder
import ktaf.rendering.frames.html.HTMLRegionMapFrameBuilder
import ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HTMLRegionMapFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HTMLRegionMapFrameBuilder(HTMLPageBuilder())
        val regionMaker = RegionMaker("", "")
        regionMaker[0, 0, 0] = Room("", "")
        regionMaker[1, 0, 0] = Room("", "")
        regionMaker[2, 0, 0] = Room("", "")
        val region = regionMaker.make()

        // When
        val result = builder.build(region, GameTestHelper.getBlankGame()).toString()
        print(result)

        // Then
        Assertions.assertTrue(result.length > 1)
    }
}
