package ktaf.interpretation

import ktaf.commands.game.Talk
import ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CharacterCommandInterpreterTest {
    @Test
    fun `given get supported commands then one element returned`() {
        // Given
        val interpreter = CharacterCommandInterpreter()

        // When
        val result = interpreter.supportedCommands

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given get contextual command help then empty list returned`() {
        // Given
        val interpreter = CharacterCommandInterpreter()

        // When
        val result = interpreter.getContextualCommandHelp(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `given abc when interpret then return fail`() {
        // Given
        val interpreter = CharacterCommandInterpreter()

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result == InterpretationResult.fail)
    }

    @Test
    fun `given talk when interpret then return instance of talk`() {
        // Given
        val interpreter = CharacterCommandInterpreter()

        // When
        val result = interpreter.interpret(CharacterCommandInterpreter.TALK, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Talk)
    }

    @Test
    fun `given talk when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = CharacterCommandInterpreter()

        // When
        val result = interpreter.interpret(CharacterCommandInterpreter.TALK, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given talk short when interpret then return instance of talk`() {
        // Given
        val interpreter = CharacterCommandInterpreter()

        // When
        val result = interpreter.interpret(CharacterCommandInterpreter.TALK_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Talk)
    }

    @Test
    fun `given talk short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = CharacterCommandInterpreter()

        // When
        val result = interpreter.interpret(CharacterCommandInterpreter.TALK_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }
}
