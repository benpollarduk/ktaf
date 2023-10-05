package com.github.benpollarduk.ktaf.assets.location

import com.github.benpollarduk.ktaf.assets.locations.Overworld
import com.github.benpollarduk.ktaf.assets.locations.Region
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OverworldTest {
    @Test
    fun `given no regions when find is called then return null`() {
        // Given
        val overworld = Overworld("", "")

        // When
        val result = overworld.findRegion("")

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given some regions when find is called with an invalid name then return null`() {
        // Given
        val overworld = Overworld("", "")
        overworld.addRegion(Region("Test", ""))

        // When
        val result = overworld.findRegion("")

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given some regions when find is called with a valid name then return the region`() {
        // Given
        val overworld = Overworld("", "")
        overworld.addRegion(Region("Test", ""))

        // When
        val result = overworld.findRegion("Test")

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given no regions when add region then currentRegion not null`() {
        // Given
        val overworld = Overworld("", "")

        // When
        overworld.addRegion(Region("Test", ""))

        // Then
        Assertions.assertNotNull(overworld.currentRegion)
    }

    @Test
    fun `given one region when remove region then currentRegion null`() {
        // Given
        val overworld = Overworld("", "")
        val region = Region("", "")
        overworld.addRegion(region)

        // When
        overworld.removeRegion(region)

        // Then
        Assertions.assertNull(overworld.currentRegion)
    }

    @Test
    fun `given two region when move to third region then return false`() {
        // Given
        val overworld = Overworld("", "")
        val region1 = Region("", "")
        val region2 = Region("", "")
        val region3 = Region("", "")
        overworld.addRegion(region1)
        overworld.addRegion(region2)

        // When
        val result = overworld.move(region3)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given two region when move to second region then return true`() {
        // Given
        val overworld = Overworld("", "")
        val region1 = Region("", "")
        val region2 = Region("", "")
        overworld.addRegion(region1)
        overworld.addRegion(region2)

        // When
        val result = overworld.move(region2)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given two region when move to second region then currentRegion is second region`() {
        // Given
        val overworld = Overworld("", "")
        val region1 = Region("", "")
        val region2 = Region("", "")
        overworld.addRegion(region1)
        overworld.addRegion(region2)

        // When
        overworld.move(region2)

        // Then
        Assertions.assertEquals(region2, overworld.currentRegion)
    }
}
