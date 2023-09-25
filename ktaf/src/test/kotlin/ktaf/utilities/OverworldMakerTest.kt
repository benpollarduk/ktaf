package ktaf.utilities

import ktaf.assets.locations.Room
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OverworldMakerTest {
    @Test
    fun `given overworld with one region when make then not null`() {
        // Given
        val regionMaker = RegionMaker("", "").also {
            it[0, 0, 0] = Room("", "")
        }
        val overworldMaker = OverworldMaker("", "", listOf(regionMaker))

        // When
        val result = overworldMaker.make()

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given overworld with one region when make then overworld has one region`() {
        // Given
        val regionMaker = RegionMaker("", "").also {
            it[0, 0, 0] = Room("", "")
        }
        val overworldMaker = OverworldMaker("", "", listOf(regionMaker))

        // When
        val result = overworldMaker.make()

        // Then
        Assertions.assertEquals(1, result.regions.size)
    }
}
