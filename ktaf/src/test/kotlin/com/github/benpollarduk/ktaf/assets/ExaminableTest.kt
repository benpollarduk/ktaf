package com.github.benpollarduk.ktaf.assets

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.CustomCommand
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExaminableTest {
    @Test
    fun `given item with defaults when calling examine then return empty string`() {
        // Given
        val examinable = Item("", "")

        // When
        val result = examinable.examine()

        // Then
        Assertions.assertEquals("", result.description)
    }

    @Test
    fun `given item with description when calling examine then result contains description`() {
        // Given
        val examinable = Item("Test", "ABC")

        // When
        val result = examinable.examine()

        // Then
        Assertions.assertTrue(result.description.contains("ABC"))
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
        val result = examinable.examine()

        // Then
        Assertions.assertTrue(result.description.contains("ABC"))
    }
}
