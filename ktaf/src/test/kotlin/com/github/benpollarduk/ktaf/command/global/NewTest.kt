package com.github.benpollarduk.ktaf.command.global

import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.global.New
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NewTest {
    @Test
    fun `given valid game when invoke then return ok`() {
        // Given
        val command = New()

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
