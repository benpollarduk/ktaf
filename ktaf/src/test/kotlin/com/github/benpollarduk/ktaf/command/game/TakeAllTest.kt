package com.github.benpollarduk.ktaf.command.game

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.game.TakeAll
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TakeAllTest {
    @Test
    fun `given no items in room when invoke then return error`() {
        // Given
        val command = TakeAll()

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given item in room when invoke then return ok`() {
        // Given
        val command = TakeAll()
        val game = GameTestHelper.getBlankGame()
        game.overworld.currentRegion?.currentRoom?.addItem(Item("", "", true))

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
