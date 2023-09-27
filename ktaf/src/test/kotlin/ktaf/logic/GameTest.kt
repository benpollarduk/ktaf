package ktaf.logic

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import ktaf.io.AdjustInput
import ktaf.io.ClearOutput
import ktaf.io.DisplayTextOutput
import ktaf.io.IOConfiguration
import ktaf.io.WaitForAcknowledge
import ktaf.io.WaitForCommand
import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.FrameBuilderCollection
import ktaf.utilities.OverworldMaker
import ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameTest {
    @Test
    fun `given blank game with player called testplayer when find interaction target then find player`() {
        // Given
        val game = GameTestHelper.getBlankGame()

        // When
        val result = game.findInteractionTarget("TestPlayer")

        // Then
        Assertions.assertTrue(result is PlayableCharacter)
    }

    @Test
    fun `given blank game with room called testroom when find interaction target then find room`() {
        // Given
        val game = GameTestHelper.getBlankGame()

        // When
        val result = game.findInteractionTarget("TestRoom")

        // Then
        Assertions.assertTrue(result is Room)
    }

    @Test
    fun `given no active converser when start conversation called then active converser not null`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val converser = NonPlayableCharacter("", "")

        // When
        game.startConversation(converser)
        val result = game.activeConverser

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given active converser when end conversation called then active converser null`() {
        // Given
        val game = GameTestHelper.getBlankGame()
        val converser = NonPlayableCharacter("", "")
        game.startConversation(converser)

        // When
        game.endConversation()
        val result = game.activeConverser

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given create then not null`() {
        // Given
        val player = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        regionMaker[0, 0, 0] = Room("TestRoom", "")
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val result = Game.create(
            "",
            "",
            "",
            "",
            { overworldMaker.make() },
            { player },
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded }
        )

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given default game when execute then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val player = PlayableCharacter("TestPlayer", "")
            val regionMaker = RegionMaker("TestRegion", "")
            regionMaker[0, 0, 0] = Room("TestRoom", "")
            val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
            val io = object : IOConfiguration {
                override val displayTextOutput: DisplayTextOutput
                    get() = object : DisplayTextOutput {
                        override fun invoke(value: String) {
                            println(value)
                            println()
                            println()
                        }
                    }
                override val waitForAcknowledge: WaitForAcknowledge
                    get() = object : WaitForAcknowledge {
                        override fun invoke(): Boolean {
                            return true
                        }
                    }
                override val waitForCommand: WaitForCommand
                    get() = object : WaitForCommand {
                        override fun invoke(): String {
                            return "Exit"
                        }
                    }
                override val clearOutput: ClearOutput
                    get() = object : ClearOutput {
                        override fun invoke() = Unit
                    }
                override val adjustInput: AdjustInput
                    get() = object : AdjustInput {
                        override fun invoke(allowInput: Boolean, cursorPosition: FramePosition) = Unit
                    }
                override val frameBuilders: FrameBuilderCollection
                    get() = AnsiConsoleConfiguration.frameBuilders
            }

            val game = Game.create(
                "",
                "",
                "",
                "",
                { overworldMaker.make() },
                { player },
                { EndCheckResult.notEnded },
                { EndCheckResult.notEnded },
                ioConfiguration = io,
                exitMode = ExitMode.EXIT_APPLICATION
            )

            // When
            Game.execute(game)
        }
    }
}
