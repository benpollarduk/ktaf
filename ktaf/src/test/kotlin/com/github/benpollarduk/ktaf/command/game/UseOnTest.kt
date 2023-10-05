package com.github.benpollarduk.ktaf.command.game

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.game.UseOn
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UseOnTest {
    @Test
    fun `given null item when invoke then return error`() {
        // Given
        val command = UseOn(null, null)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given null target when invoke then return error`() {
        // Given
        val command = UseOn(Item("", ""), null)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given valid item and target when invoke then return ok`() {
        // Given
        val item = Item("", "")
        val game = GameTestHelper.getBlankGame()
        val command = UseOn(item, game.player)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
