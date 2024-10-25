package com.github.benpollarduk.ktaf.command.game

import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.game.Talk
import com.github.benpollarduk.ktaf.conversations.Conversation
import com.github.benpollarduk.ktaf.conversations.Paragraph
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TalkTest {
    @Test
    fun `given null converser when invoke then return error`() {
        // Given
        val command = Talk(null)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given player that can't converse when invoke then return error`() {
        // Given
        val converser = NonPlayableCharacter("", "")
        val command = Talk(converser)
        val game = GameTestHelper.getBlankGame().also {
            it.changePlayer(PlayableCharacter("", "", false))
        }

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given dead converser when invoke then return error`() {
        // Given
        val converser = NonPlayableCharacter("", "").also {
            it.kill()
        }
        val command = Talk(converser)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given live converser in room when invoke then return internal`() {
        // Given
        val converser = NonPlayableCharacter("", "").also {
            it.conversation = Conversation(listOf(Paragraph("")))
        }
        val command = Talk(converser)
        val game = GameTestHelper.getBlankGame()
        game.overworld.currentRegion?.currentRoom?.addCharacter(converser)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.INTERNAL, result.result)
    }
}
