package com.github.benpollarduk.ktaf.command.game

import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.commands.game.Move
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MoveTest {
    @Test
    fun `given an invalid move when invoke then return error`() {
        // Given
        val command = Move(Direction.EAST)

        // When
        val result = command.invoke(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(ReactionResult.ERROR, result.result)
    }

    @Test
    fun `given a valid move when invoke then return ok`() {
        // Given
        val command = Move(Direction.EAST)
        val game = GameTestHelper.getBlankGame()
        val secondRoom = Room("", "", listOf(Exit(Direction.WEST)))
        game.overworld.currentRegion?.currentRoom?.addExit(Exit(Direction.EAST))
        game.overworld.currentRegion?.addRoom(secondRoom, 1, 0, 0)

        // When
        val result = command.invoke(game)

        // Then
        Assertions.assertEquals(ReactionResult.OK, result.result)
    }
}
