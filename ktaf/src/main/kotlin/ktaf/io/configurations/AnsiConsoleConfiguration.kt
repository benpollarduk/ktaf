package ktaf.io.configurations

import ktaf.io.AdjustInput
import ktaf.io.ClearOutput
import ktaf.io.DisplayTextOutput
import ktaf.io.IOConfiguration
import ktaf.io.WaitForAcknowledge
import ktaf.io.WaitForCommand
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.FrameBuilderCollection
import ktaf.rendering.frames.ansi.AnsiAboutFrameBuilder
import ktaf.rendering.frames.ansi.AnsiCompletionFrameBuilder
import ktaf.rendering.frames.ansi.AnsiConversationFrameBuilder
import ktaf.rendering.frames.ansi.AnsiGameOverFrameBuilder
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import ktaf.rendering.frames.ansi.AnsiHelpFrameBuilder
import ktaf.rendering.frames.ansi.AnsiRegionMapBuilder
import ktaf.rendering.frames.ansi.AnsiRegionMapFrameBuilder
import ktaf.rendering.frames.ansi.AnsiRoomMapBuilder
import ktaf.rendering.frames.ansi.AnsiSceneFrameBuilder
import ktaf.rendering.frames.ansi.AnsiTitleFrameBuilder
import ktaf.rendering.frames.ansi.AnsiTransitionFrameBuilder

/**
 * Provides an [IOConfiguration] for ANSI enabled consoles.
 */
public object AnsiConsoleConfiguration : IOConfiguration {
    private const val endOfBuffer = -1
    override val displayTextOutput: DisplayTextOutput
        get() = object : DisplayTextOutput {
            override fun invoke(value: String) {
                // print to out
                print(value)
            }
        }
    override val waitForAcknowledge: WaitForAcknowledge
        get() = object : WaitForAcknowledge {
            override fun invoke(): Boolean {
                var c = endOfBuffer
                while (c == endOfBuffer) {
                    // read character from in
                    c = System.`in`.read()
                }
                // check for enter
                return c == 10
            }
        }
    override val waitForCommand: WaitForCommand
        get() = object : WaitForCommand {
            override fun invoke(): String {
                // use standard read line
                return readLine() ?: ""
            }
        }
    override val clearOutput: ClearOutput
        get() = object : ClearOutput {
            override fun invoke() {
                // windows terminal doesn't clear properly with ANSI...
                if (System.getProperty("os.name").contains("Windows")) {
                    ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
                } else {
                    // ANSI escape code to clear the screen
                    print("\u001b[H\u001b[2J")
                    // flush output
                    System.out.flush()
                }
            }
        }
    override val adjustInput: AdjustInput
        get() = object : AdjustInput {
            override fun invoke(allowInput: Boolean, cursorPosition: FramePosition) {
                // use ANSI escape code to set the cursor position
                print("\u001b[${cursorPosition.y};${cursorPosition.x}H")
                // use ANSI escape codes to hide or show the cursor
                print(if (allowInput) "\u001b[?25h" else "\u001b[?25l")
            }
        }
    override val frameBuilders: FrameBuilderCollection
        get() {
            val gridStringBuilder = AnsiGridStringBuilder()
            return FrameBuilderCollection(
                AnsiTitleFrameBuilder(gridStringBuilder),
                AnsiAboutFrameBuilder(gridStringBuilder),
                AnsiHelpFrameBuilder(gridStringBuilder),
                AnsiTransitionFrameBuilder(gridStringBuilder),
                AnsiCompletionFrameBuilder(gridStringBuilder),
                AnsiGameOverFrameBuilder(gridStringBuilder),
                AnsiConversationFrameBuilder(gridStringBuilder),
                AnsiSceneFrameBuilder(gridStringBuilder, AnsiRoomMapBuilder()),
                AnsiRegionMapFrameBuilder(gridStringBuilder, AnsiRegionMapBuilder())
            )
        }
}
