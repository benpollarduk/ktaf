package com.github.benpollarduk.ktaf.command.conversation

import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.conversation.End
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EndTest {
    @Test
    fun `given valid game when invoke then return ok`() {
        // Given
        val command = End()

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
