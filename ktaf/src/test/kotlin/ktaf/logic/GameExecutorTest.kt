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
import ktaf.utilities.templates.GameTemplate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameExecutorTest {
    @Test
    fun `given default game when execute then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val template = object : GameTemplate() {
                override fun instantiate(ioConfiguration: IOConfiguration): Game {
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

                    return Game(
                        GameInformation("", "", "", ""),
                        player,
                        overworldMaker.make(),
                        { EndCheckResult.notEnded },
                        { EndCheckResult.notEnded },
                        ioConfiguration = io
                    )
                }
            }

            // When
            GameExecutor.execute(template, ExitMode.EXIT_APPLICATION, AnsiConsoleConfiguration)
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

        val template = object : GameTemplate() {
            override fun instantiate(ioConfiguration: IOConfiguration): Game {
                return game
            }
        }

        // When
        GameExecutor.executeAysnc(template, ExitMode.EXIT_APPLICATION, AnsiConsoleConfiguration)
        Thread.sleep(1000)
        GameExecutor.cancel()
        Thread.sleep(1000)
        Assertions.assertFalse(game.isExecuting)
    }
}
