package ktaf.logic

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import ktaf.io.IOConfiguration
import ktaf.io.RenderFrame
import ktaf.io.WaitForAcknowledge
import ktaf.io.WaitForCommand
import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.logic.conditions.EndCheckResult
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.FrameBuilderCollection
import ktaf.utilities.OverworldMaker
import ktaf.utilities.RegionMaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameExecutorTest {
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
                override val renderFrame: RenderFrame
                    get() = object : RenderFrame {
                        override fun invoke(frame: String, allowInput: Boolean, cursorPosition: FramePosition) {
                            println(frame)
                            println()
                            println()
                        }
                    }
                override val waitForAcknowledge: WaitForAcknowledge
                    get() = object : WaitForAcknowledge {
                        override fun invoke(cancellationToken: CancellationToken): Boolean {
                            return true
                        }
                    }
                override val waitForCommand: WaitForCommand
                    get() = object : WaitForCommand {
                        override fun invoke(cancellationToken: CancellationToken): String {
                            return "Exit"
                        }
                    }
                override val frameBuilders: FrameBuilderCollection
                    get() = AnsiConsoleConfiguration.frameBuilders
            }

            val game = Game(
                GameInformation("", "", "", ""),
                player,
                overworldMaker.make(),
                { EndCheckResult.notEnded },
                { EndCheckResult.notEnded },
                ioConfiguration = io
            )

            // When
            GameExecutor.execute(game, ExitMode.EXIT_APPLICATION)
        }
    }

    @Test
    fun `given game executing async game when cancel then game state equals finished`() {
        // Given
        val player = PlayableCharacter("TestPlayer", "")
        val regionMaker = RegionMaker("TestRegion", "")
        regionMaker[0, 0, 0] = Room("TestRoom", "")
        val overworldMaker = OverworldMaker("TestOverworld", "", listOf(regionMaker))
        val io = object : IOConfiguration {
            override val renderFrame: RenderFrame
                get() = object : RenderFrame {
                    override fun invoke(frame: String, allowInput: Boolean, cursorPosition: FramePosition) {
                        println(frame)
                        println()
                        println()
                    }
                }
            override val waitForAcknowledge: WaitForAcknowledge
                get() = object : WaitForAcknowledge {
                    override fun invoke(cancellationToken: CancellationToken): Boolean {
                        return true
                    }
                }
            override val waitForCommand: WaitForCommand
                get() = object : WaitForCommand {
                    override fun invoke(cancellationToken: CancellationToken): String {
                        return "Exit"
                    }
                }
            override val frameBuilders: FrameBuilderCollection
                get() = AnsiConsoleConfiguration.frameBuilders
        }

        val game = Game(
            GameInformation("", "", "", ""),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded },
            ioConfiguration = io
        )

        // When
        GameExecutor.executeAysnc(game, ExitMode.EXIT_APPLICATION)
        Thread.sleep(1000)
        GameExecutor.cancelAysnc()
        Thread.sleep(1000)
        Assertions.assertEquals(GameState.FINISHED, game.state)
    }
}
