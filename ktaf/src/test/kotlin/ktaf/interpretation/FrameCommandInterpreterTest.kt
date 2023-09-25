package ktaf.interpretation

import ktaf.commands.frame.CommandsOff
import ktaf.commands.frame.CommandsOn
import ktaf.commands.frame.KeyOff
import ktaf.commands.frame.KeyOn
import ktaf.logic.GameTestHelper
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
        val result = interpreter.interpret(FrameCommandInterpreter.commandsOff, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is CommandsOff)
    }

    @Test
    fun `given commandsoff when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.commandsOff, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given commandson when interpret then return instance of commands on`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.commandsOn, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is CommandsOn)
    }

    @Test
    fun `given commandson when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.commandsOn, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given keyoff when interpret then return instance of key off`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.keyOff, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is KeyOff)
    }

    @Test
    fun `given keyoff when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.keyOff, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given keyon when interpret then return instance of key on`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.keyOn, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is KeyOn)
    }

    @Test
    fun `given keyon when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = FrameCommandInterpreter()

        // When
        val result = interpreter.interpret(FrameCommandInterpreter.keyOn, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }
}
