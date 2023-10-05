package com.github.benpollarduk.ktaf.interpretation

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.commands.game.Drop
import com.github.benpollarduk.ktaf.commands.game.Examine
import com.github.benpollarduk.ktaf.commands.game.Take
import com.github.benpollarduk.ktaf.commands.game.UseOn
import com.github.benpollarduk.ktaf.logic.Game
import com.github.benpollarduk.ktaf.logic.GameInformation
import com.github.benpollarduk.ktaf.logic.GameTestHelper
import com.github.benpollarduk.ktaf.logic.conditions.EndCheckResult
import com.github.benpollarduk.ktaf.utilities.OverworldMaker
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ItemCommandInterpreterTest {
    @Test
    fun `given get supported commands then five elements returned`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.supportedCommands

        // Then
        Assertions.assertEquals(5, result.size)
    }

    @Test
    fun `given get contextual command help then one element returned`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.getContextualCommandHelp(GameTestHelper.getBlankGame())

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given abc when interpret then return fail`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret("abc", GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result == InterpretationResult.fail)
    }

    @Test
    fun `given drop when interpret then return instance of drop`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.DROP, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Drop)
    }

    @Test
    fun `given drop when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.DROP, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given drop short when interpret then return instance of drop`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.DROP_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Drop)
    }

    @Test
    fun `given drop short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.DROP_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given examine when interpret then return instance of examine`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.EXAMINE, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Examine)
    }

    @Test
    fun `given examine when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.EXAMINE, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given examine short when interpret then return instance of examine`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.EXAMINE_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Examine)
    }

    @Test
    fun `given examine short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.EXAMINE_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given take when interpret then return instance of take`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.TAKE, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Take)
    }

    @Test
    fun `given take when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.TAKE, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given take short when interpret then return instance of take`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.TAKE_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.command is Take)
    }

    @Test
    fun `given take short when interpret then interpreted successfully is true`() {
        // Given
        val interpreter = ItemCommandInterpreter()

        // When
        val result = interpreter.interpret(ItemCommandInterpreter.TAKE_SHORT, GameTestHelper.getBlankGame())

        // Then
        Assertions.assertTrue(result.interpretedSuccessfully)
    }

    @Test
    fun `given use ball when interpret with ball in room then interpreted command is useon`() {
        // Given
        val player = PlayableCharacter("Test Player", "This is a test player.")
        val regionMaker = RegionMaker("Test Region", "This is a test region.")
        regionMaker[0, 0, 0] = Room("Test Room", "This is just a test room.").also {
            it.addItem(Item("Ball", "A ball is on the floor!"))
        }
        val overworldMaker = OverworldMaker("Test Overworld", "This is a test overworld", listOf(regionMaker))
        val game = Game(
            GameInformation("Test game", "This is a test game", "This is a test game", "Test author"),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )
        val itemCommandInterpreter = ItemCommandInterpreter()

        // When
        val result = itemCommandInterpreter.interpret("Use ball", game)

        // Then
        Assertions.assertTrue(result.command is UseOn)
    }

    @Test
    fun `given use ball on me when interpret with ball in room then interpreted command is useon`() {
        // Given
        val player = PlayableCharacter("Test Player", "This is a test player.")
        val regionMaker = RegionMaker("Test Region", "This is a test region.")
        regionMaker[0, 0, 0] = Room("Test Room", "This is just a test room.").also {
            it.addItem(Item("Ball", "A ball is on the floor!"))
        }
        val overworldMaker = OverworldMaker("Test Overworld", "This is a test overworld", listOf(regionMaker))
        val game = Game(
            GameInformation("Test game", "This is a test game", "This is a test game", "Test author"),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )
        val itemCommandInterpreter = ItemCommandInterpreter()

        // When
        val result = itemCommandInterpreter.interpret("Use ball on me", game)

        // Then
        Assertions.assertTrue(result.command is UseOn)
    }
}
