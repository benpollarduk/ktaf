package com.github.benpollarduk.ktaf.interpretation

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
    fun `given abc when interpret then return fail`() {
        // Given
        val interpreter = CustomCommandInterpreter()

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result == InterpretationResult.fail)
    }
}
