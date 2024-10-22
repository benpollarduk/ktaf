package com.github.benpollarduk.ktaf.assets

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.CustomCommand
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExaminableObjectTest {
    @Test
    fun `given item with description when calling examine then result contains description`() {
        // Given
        val examinable = Item("Test", "ABC")

        // When
        val result = examinable.examine(ExaminationScene.noScene)

        // Then
        Assertions.assertTrue(result.description.contains("ABC"))
    }

    @Test
    fun `given item with no description when calling examine then result contains name`() {
        // Given
        val examinable = Item("Test", "")

        // When
        val result = examinable.examine(ExaminationScene.noScene)

        // Then
        Assertions.assertTrue(result.description.contains("Test"))
    }

    @Test
    fun `given item with no name or description when calling examine then result contains class name`() {
        // Given
        val examinable = Item("", "")

        // When
        val result = examinable.examine(ExaminationScene.noScene)

        // Then
        Assertions.assertTrue(result.description.contains("Item"))
    }

    @Test
    fun `given item with commands when calling examine then result contains command`() {
        // Given
        val examinable = Item("Test", "")
        examinable.commands = listOf(
            CustomCommand(CommandHelp("ABC", "XYZ"), false) { _, _ ->
                Reaction(ReactionResult.OK, "")
            }
        )

        // When
        val result = examinable.examine(ExaminationScene.noScene)

        // Then
        Assertions.assertTrue(result.description.contains("ABC"))
    }

    @Test
    fun `given item with attributes when calling examine then result contains assey`() {
        // Given
        val examinable = Item("", "")
        examinable.attributes.add("attribute", 0)

        // When
        val result = examinable.examine(ExaminationScene.noScene)

        // Then
        Assertions.assertTrue(result.description.contains("attribute"))
    }
}
