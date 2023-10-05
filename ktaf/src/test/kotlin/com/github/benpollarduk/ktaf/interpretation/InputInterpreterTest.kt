package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.commands.conversation.End
import com.github.benpollarduk.ktaf.commands.game.Unactionable
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("MaxLineLength")
class InputInterpreterTest {
    @Test
    fun `given get supported commands when frame and conversation interpreters then three elements returned`() {
        // Given
        val interpreter = InputInterpreter(
            listOf(
                FrameCommandInterpreter(),
                ConversationCommandInterpreter()
            )
        )

        // When
        val result = interpreter.supportedCommands

        // Then
        Assertions.assertEquals(3, result.size)
    }

    @Test
    fun `given get contextual command help when frame and conversation interpreters then empty list returned`() {
        // Given
        val interpreter = InputInterpreter(
            listOf(
                FrameCommandInterpreter(),
                ConversationCommandInterpreter()
            )
        )

        // When
        val result = interpreter.getContextualCommandHelp(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `given abc when interpret when frame and conversation interpreters then return unactionable`() {
        // Given
        val interpreter = InputInterpreter(
            listOf(
                FrameCommandInterpreter(),
                ConversationCommandInterpreter()
            )
        )

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Unactionable)
    }

    @Test
    fun `given abc when interpret when frame and conversation interpreters then interpreted successfully is false`() {
        // Given
        val interpreter = InputInterpreter(
            listOf(
                FrameCommandInterpreter(),
                ConversationCommandInterpreter()
            )
        )

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertFalse(result.interpretedSuccessfully)
    }

    @Test
    fun `given end when interpret with active converser and frame and conversation interpreters then return instance of end`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val converser = NonPlayableCharacter("", "")
        game.startConversation(converser)
        val interpreter = InputInterpreter(
            listOf(
                FrameCommandInterpreter(),
                ConversationCommandInterpreter()
            )
        )

        // When
        val result = interpreter.interpret(ConversationCommandInterpreter.END, game)

        // Then
        Assertions.assertTrue(result.command is End)
    }

    @Test
    fun `given end when interpret without active converser and frame and conversation interpreters then return unactionable`() {
        // Given
        val interpreter = InputInterpreter(
            listOf(
                FrameCommandInterpreter(),
                ConversationCommandInterpreter()
            )
        )

        // When
        val result = interpreter.interpret(ConversationCommandInterpreter.END, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Unactionable)
    }

    @Test
    fun `given end when interpret with active converser and frame and conversation interpreters then interpreted successfully is true`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val converser = NonPlayableCharacter("", "")
        game.startConversation(converser)
        val interpreter = InputInterpreter(
            listOf(
                FrameCommandInterpreter(),
                ConversationCommandInterpreter()
            )
        )

        // When
        val result = interpreter.interpret(ConversationCommandInterpreter.END, game)

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given end when interpret with no active converser and frame and conversation interpreters then interpreted successfully is false`() {
        // Given
        val interpreter = InputInterpreter(
            listOf(
                FrameCommandInterpreter(),
                ConversationCommandInterpreter()
            )
        )

        // When
        val result = interpreter.interpret(ConversationCommandInterpreter.END, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertFalse(result.interpretedSuccessfully)
    }
}
