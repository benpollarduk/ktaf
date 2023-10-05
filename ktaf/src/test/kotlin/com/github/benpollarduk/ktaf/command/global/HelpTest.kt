package com.github.benpollarduk.ktaf.command.global

import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.global.Help
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HelpTest {
    @Test
    fun `given valid game when invoke then return internal`() {
        // Given
        val command = Help()

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.INTERNAL, result.result)
    }
}
