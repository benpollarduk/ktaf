package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.CustomCommand
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CustomCommandInterpreterTest {
    @Test
    fun `given get supported commands then empty list returned`() {
        // Given
        val interpreter = CustomCommandInterpreter()

        // When
        val result = interpreter.supportedCommands

        // Then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `given get contextual command help then empty list returned`() {
        // Given
        val interpreter = CustomCommandInterpreter()

        // When
        val result = interpreter.getContextualCommandHelp(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `given get contextual command help when some commands then non-empty list returned`() {
        // Given
        val interpreter = CustomCommandInterpreter()
        val game = GameTestHelper.getBlankGame()
        game.player.commands = listOf(
            CustomCommand(
                CommandHelp(
                    "Test",
                    "Test"
                ),
                true
            ) { _, _ ->
                Reaction(ReactionResult.OK, "")
            }
        )

        // When
        val result = interpreter.getContextualCommandHelp(game)

        // Then
        Assertions.assertTrue(result.isNotEmpty())
    }

    @Test
    fun `given abc when interpret then return fail`() {
        // Given
        val interpreter = CustomCommandInterpreter()

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result == InterpretationResult.fail)
    }

    @Test
    fun `given test when interpret then interpret successfully`() {
        // Given
        val interpreter = CustomCommandInterpreter()
        val game = GameTestHelper.getBlankGame()
        game.player.commands = listOf(
            CustomCommand(
                CommandHelp(
                    "Test",
                    "Test"
                ),
                true
            ) { _, _ ->
                Reaction(ReactionResult.OK, "")
            }
        )

        // When
        val result = interpreter.interpret("test", game)

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }
}
