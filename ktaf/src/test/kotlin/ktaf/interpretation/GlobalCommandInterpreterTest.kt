package ktaf.interpretation

import ktaf.commands.global.About
import ktaf.commands.global.Exit
import ktaf.commands.global.Help
import ktaf.commands.global.Map
import ktaf.commands.global.New
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GlobalCommandInterpreterTest {
    @Test
    fun `given get supported commands then five elements returned`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.supportedCommands

        // Then
        Assertions.assertEquals(5, result.size)
    }

    @Test
    fun `given get contextual command help then empty list returned`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.getContextualCommandHelp(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `given abc when interpret then return fail`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result == InterpretationResult.fail)
    }

    @Test
    fun `given about when interpret then return instance of about`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.ABOUT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is About)
    }

    @Test
    fun `given about when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.ABOUT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given exit when interpret then return instance of exit`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.EXIT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Exit)
    }

    @Test
    fun `given exit when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.EXIT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given help when interpret then return instance of help`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.HELP, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Help)
    }

    @Test
    fun `given help when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.HELP, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given map when interpret then return instance of map`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.MAP, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Map)
    }

    @Test
    fun `given map when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.MAP, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given new when interpret then return instance of new`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.NEW, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is New)
    }

    @Test
    fun `given new when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = GlobalCommandInterpreter()

        // When
        val result = interpreter.interpret(GlobalCommandInterpreter.NEW, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }
}
