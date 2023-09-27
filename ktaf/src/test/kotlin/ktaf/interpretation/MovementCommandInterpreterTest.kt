package ktaf.interpretation

import ktaf.commands.game.Move
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MovementCommandInterpreterTest {
    @Test
    fun `given get supported commands then six elements returned`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.supportedCommands

        // Then
        Assertions.assertEquals(6, result.size)
    }

    @Test
    fun `given get contextual command help then empty list returned`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.getContextualCommandHelp(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `given abc when interpret then return fail`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result == InterpretationResult.fail)
    }

    @Test
    fun `given north when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.NORTH, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given north when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.NORTH, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given north short when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.NORTH_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given north short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.NORTH_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given south when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.SOUTH, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given south when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.SOUTH, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given south short when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.SOUTH_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given south short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.SOUTH_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given east when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.EAST, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given east when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.EAST, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given east short when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.EAST_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given east short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.EAST_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given west when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.WEST, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given west when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.WEST, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given west short when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.WEST_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given west short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.WEST_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given up when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.UP, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given up when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.UP, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given up short when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.UP_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given up short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.UP_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given down when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.DOWN, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given down when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.DOWN, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given down short when interpret then return instance of move`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.DOWN_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Move)
    }

    @Test
    fun `given down short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = MovementCommandInterpreter()

        // When
        val result = interpreter.interpret(MovementCommandInterpreter.DOWN_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }
}
