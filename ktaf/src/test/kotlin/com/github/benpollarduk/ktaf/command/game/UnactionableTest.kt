package com.github.benpollarduk.ktaf.command.game

import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.game.Unactionable
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UnactionableTest {
    @Test
    fun `given valid game when invoke then return error`() {
        // Given
        val command = Unactionable("")

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }
}
