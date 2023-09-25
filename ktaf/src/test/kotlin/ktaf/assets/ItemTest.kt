package ktaf.assets

import ktaf.assets.interaction.InteractionEffect
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ItemTest {
    @Test
    fun `given two items when used on each other then no effect`() {
        // Given
        val itemA = Item("A", "")
        val itemB = Item("B", "")

        // When
        val result = itemA.use(itemB)

        // Then
        Assertions.assertEquals(InteractionEffect.NO_EFFECT, result.effect)
    }

    @Test
    fun `given item a and item b when item b is morphed in to item a then item a has item b identifier`() {
        // Given
        val itemA = Item("A", "")
        val itemB = Item("B", "")

        // When
        itemA.morph(itemB)

        // Then
        Assertions.assertEquals(itemB.identifier, itemA.identifier)
    }

    @Test
    fun `given item a and item b when item b is morphed in to item a then item a has item b description`() {
        // Given
        val itemA = Item("A", "")
        val itemB = Item("B", "")

        // When
        itemA.morph(itemB)

        // Then
        Assertions.assertEquals(itemB.description, itemA.description)
    }
}
