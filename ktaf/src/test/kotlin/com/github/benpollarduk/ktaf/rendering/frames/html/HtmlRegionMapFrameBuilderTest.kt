package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.rendering.frames.GridRegionMapBuilder
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlRegionMapFrameBuilderTest {
    @Test
    fun `given defaults when build then string with some length returned`() {
        // Given
        val builder = HtmlRegionMapFrameBuilder(HtmlPageBuilder(), GridRegionMapBuilder(), Size(80, 50))
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
