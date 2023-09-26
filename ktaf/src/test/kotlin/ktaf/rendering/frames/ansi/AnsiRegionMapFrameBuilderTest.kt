package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import ktaf.assets.locations.Room
import ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnsiRegionMapFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = AnsiRegionMapFrameBuilder(AnsiGridStringBuilder(), AnsiRegionMapBuilder(), Size(80, 50))
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
