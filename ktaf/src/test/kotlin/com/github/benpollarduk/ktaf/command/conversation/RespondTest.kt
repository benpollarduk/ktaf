package com.github.benpollarduk.ktaf.command.conversation

import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.conversation.Respond
import com.github.benpollarduk.ktaf.conversations.Response
import com.github.benpollarduk.ktaf.conversations.instructions.Next
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RespondTest {
    @Test
    fun `given no converser when invoke then return error`() {
        // Given
        val command = Respond(Response("", Next()))

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given converser when invoke with invalid response then return error`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val npc = NonPlayableCharacter("", "")
        val command = Respond(Response("", Next()))
        game.startConversation(npc)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given converser when invoke with valid response then return internal`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val response = Response("", Next())
        val npc = NonPlayableCharacter("", "")
        val command = Respond(response)
        game.startConversation(npc)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }
}
