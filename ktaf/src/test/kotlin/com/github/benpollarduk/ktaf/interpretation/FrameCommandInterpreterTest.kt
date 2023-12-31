package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.commands.frame.CommandsOff
import com.github.benpollarduk.ktaf.commands.frame.CommandsOn
import com.github.benpollarduk.ktaf.commands.frame.KeyOff
import com.github.benpollarduk.ktaf.commands.frame.KeyOn
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FrameCommandInterpreterTest {
    @Test
    fun `given get supported commands then two elements returned`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.supportedCommands

        // Then
        Assertions.assertEquals(2, result.size)
    }

    @Test
    fun `given get contextual command help then empty list returned`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.getContextualCommandHelp(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `given abc when interpret then return fail`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result == InterpretationResult.fail)
    }

    @Test
    fun `given commandsoff when interpret then return instance of commands off`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.COMMANDS_OFF, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is CommandsOff)
    }

    @Test
    fun `given commandsoff when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.COMMANDS_OFF, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given commandson when interpret then return instance of commands on`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.COMMANDS_ON, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is CommandsOn)
    }

    @Test
    fun `given commandson when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.COMMANDS_ON, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given keyoff when interpret then return instance of key off`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.KEY_OFF, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is KeyOff)
    }

    @Test
    fun `given keyoff when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.KEY_OFF, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given keyon when interpret then return instance of key on`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.KEY_ON, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is KeyOn)
    }

    @Test
    fun `given keyon when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.KEY_ON, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }
}
