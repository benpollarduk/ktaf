package com.github.benpollarduk.ktaf.command.game

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.game.Drop
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DropTest {
    @Test
    fun `given null item when invoke then return error`() {
        // Given
        val command = Drop(null)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given player doesn't have item when invoke then return error`() {
        // Given
        val item = Item("", "")
        val command = Drop(item)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given player has item when invoke then return ok`() {
        // Given
        val item = Item("", "")
        val command = Drop(item)
        val game = GameTestHelper.getBlankGame()
        game.player.acquireItem(item)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
