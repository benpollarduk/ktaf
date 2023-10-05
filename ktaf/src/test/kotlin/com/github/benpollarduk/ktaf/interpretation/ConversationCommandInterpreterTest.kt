package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.commands.conversation.End
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ConversationCommandInterpreterTest {
    @Test
    fun `given get supported commands then one element returned`() {
        // Given
        val interpreter = ConversationCommandInterpreter()

        // When
        val result = interpreter.supportedCommands

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given get contextual command help then empty list returned`() {
        // Given
        val interpreter = ConversationCommandInterpreter()

        // When
        val result = interpreter.getContextualCommandHelp(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `given abc when interpret then return fail`() {
        // Given
        val interpreter = ConversationCommandInterpreter()

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result == InterpretationResult.fail)
    }

    @Test
    fun `given end when interpret with active converser then return instance of end`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val converser = NonPlayableCharacter("", "")
        game.startConversation(converser)
        val interpreter = ConversationCommandInterpreter()

        // When
        val result = interpreter.interpret(ConversationCommandInterpreter.END, game)

        // Then
        Assertions.assertTrue(result.command is End)
    }

    @Test
    fun `given end when interpret without active converser then return fail`() {
        // Given
        val interpreter = ConversationCommandInterpreter()

        // When
        val result = interpreter.interpret(ConversationCommandInterpreter.END, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result == InterpretationResult.fail)
    }

    @Test
    fun `given end when interpret with active converser then interpreted successfully is true`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val converser = NonPlayableCharacter("", "")
        game.startConversation(converser)
        val interpreter = ConversationCommandInterpreter()

        // When
        val result = interpreter.interpret(ConversationCommandInterpreter.END, game)

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given end when interpret with no active converser then interpreted successfully is false`() {
        // Given
        val interpreter = ConversationCommandInterpreter()

        // When
        val result = interpreter.interpret(ConversationCommandInterpreter.END, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertFalse(result.interpretedSuccessfully)
    }
}
