package ktaf.utilities

import ktaf.assets.Item
import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.locations.Direction
import ktaf.assets.locations.Exit
import ktaf.assets.locations.Room
import ktaf.assets.locations.ViewPoint
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StringUtilitiesTest {
    @Test
    fun `given a ball an armchair and a pig list when constructExaminablesToSentence then return a ball, an armchair and a pig full stop`() {
        // Given
        val examinables = listOf(Item("Ball", ""), Item("Armchair", ""), Item("Pig", ""))

        // When
        val result = StringUtilities.constructExaminablesAsSentence(examinables)

        // Then
        Assertions.assertEquals("A Ball, an Armchair and a Pig.", result)
    }

    @Test
    fun `given one two three when cut line from paragraph and max width of 100 then extracted word is one two three and rest is empty`() {
        // Given
        val value = "one two three"

        // When
        val result = StringUtilities.cutLineFromParagraph(value, 100)

        // Then
        Assertions.assertEquals("one two three", result.extractedWord)
        Assertions.assertEquals("", result.rest)
    }

    @Test
    fun `given one two three when cut line from paragraph and max width of 5 then extracted word is one and rest is two three`() {
        // Given
        val value = "one two three"

        // When
        val result = StringUtilities.cutLineFromParagraph(value, 5)

        // Then
        Assertions.assertEquals("one", result.extractedWord)
        Assertions.assertEquals("two three", result.rest)
    }

    @Test
    fun `given one two three when extract next word from string then extracted word is one and rest is two three`() {
        // Given
        val value = "one two three"

        // When
        val result = StringUtilities.extractNextWordFromString(value)

        // Then
        Assertions.assertEquals("one", result.extractedWord)
        Assertions.assertEquals(" two three", result.rest)
    }

    @Test
    fun `given no characters when create NPC string then return empty string`() {
        // Given
        val value = Room("", "")

        // When
        val result = StringUtilities.createNPCString(value)

        // Then
        Assertions.assertEquals("", result)
    }

    @Test
    fun `given character b when create NPC string then return b is in a`() {
        // Given
        val value = Room("a", "").also {
            it.addCharacter(NonPlayableCharacter("b", ""))
        }

        // When
        val result = StringUtilities.createNPCString(value)

        // Then
        Assertions.assertEquals("b is in a.", result)
    }

    @Test
    fun `given character b and c when create NPC string then return b and c are in the a`() {
        // Given
        val value = Room("a", "").also {
            it.addCharacter(NonPlayableCharacter("b", ""))
            it.addCharacter(NonPlayableCharacter("c", ""))
        }

        // When
        val result = StringUtilities.createNPCString(value)

        // Then
        Assertions.assertEquals("b and c are in the a.", result)
    }

    @Test
    fun `given character b and c and d when create NPC string then return b, c and d are in the a`() {
        // Given
        val value = Room("a", "").also {
            it.addCharacter(NonPlayableCharacter("b", ""))
            it.addCharacter(NonPlayableCharacter("c", ""))
            it.addCharacter(NonPlayableCharacter("d", ""))
        }

        // When
        val result = StringUtilities.createNPCString(value)

        // Then
        Assertions.assertEquals("b, c and d are in the a.", result)
    }

    @Test
    fun `given no view when create viewpoint string then return empty string`() {
        // Given
        val regionMaker = RegionMaker("", "")
        val startRoom = Room("", "")
        regionMaker[0, 0, 0] = startRoom
        val region = regionMaker.make()
        val viewPoint = ViewPoint(region)

        // When
        val result = StringUtilities.createViewpointAsString(startRoom, viewPoint)

        // Then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `given a view when create viewpoint string then return non empty string`() {
        // Given
        val regionMaker = RegionMaker("", "")
        val startRoom = Room("", "", listOf(Exit(Direction.EAST)))
        regionMaker[0, 0, 0] = startRoom
        regionMaker[1, 0, 0] = Room("", "", listOf(Exit(Direction.WEST)))
        val region = regionMaker.make()
        val viewPoint = ViewPoint(region)

        // When
        val result = StringUtilities.createViewpointAsString(startRoom, viewPoint)

        // Then
        Assertions.assertTrue(result.isNotEmpty())
    }
}
