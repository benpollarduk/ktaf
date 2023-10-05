package com.github.benpollarduk.ktaf.io.configurations

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.io.IOConfiguration
import com.github.benpollarduk.ktaf.io.RenderFrame
import com.github.benpollarduk.ktaf.io.WaitForAcknowledge
import com.github.benpollarduk.ktaf.io.WaitForCommand
import com.github.benpollarduk.ktaf.logic.CancellationToken
import com.github.benpollarduk.ktaf.rendering.FramePosition
import com.github.benpollarduk.ktaf.rendering.frames.FrameBuilderCollection
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiAboutFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiCompletionFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiConversationFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiGameOverFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiHelpFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiRegionMapBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiRegionMapFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiRoomMapBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiSceneFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiTitleFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.ansi.AnsiTransitionFrameBuilder

/**
 * Provides an [IOConfiguration] for ANSI enabled consoles.
 */
public object AnsiConsoleConfiguration : IOConfiguration {
    private const val END_OF_BUFFER = -1
    private const val LINE_FEED = 10
    override val renderFrame: RenderFrame
        get() = object : RenderFrame {
            override fun invoke(
                frame: String,
                allowInput: Boolean,
                cursorPosition: FramePosition
            ) {
                // windows terminal doesn't clear properly with ANSI...
                if (System.getProperty("os.name").contains("Windows")) {
                    ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
                } else {
                    // ANSI escape code to clear the screen
                    print("\u001b[H\u001b[2J")
                    // flush output
                    System.out.flush()
                }
                // print to out
                print(frame)
                // use ANSI escape code to set the cursor position
                print("\u001b[${cursorPosition.y};${cursorPosition.x}H")
                // use ANSI escape codes to hide or show the cursor
                print(if (allowInput) "\u001b[?25h" else "\u001b[?25l")
            }
        }
    override val waitForAcknowledge: WaitForAcknowledge
        get() = object : WaitForAcknowledge {
            override fun invoke(cancellationToken: CancellationToken): Boolean {
                var c = END_OF_BUFFER
                while (c == END_OF_BUFFER) {
                    if (cancellationToken.wasCancelled) {
                        return false
                    }
                    // read character from in
                    c = System.`in`.read()
                }
                // check for line feed
                return c == LINE_FEED
            }
        }
    override val waitForCommand: WaitForCommand
        get() = object : WaitForCommand {
            override fun invoke(cancellationToken: CancellationToken): String {
                // use standard read line
                return readLine() ?: ""
            }
        }
    override val frameBuilders: FrameBuilderCollection
        get() {
            val gridStringBuilder = AnsiGridStringBuilder()
            val frameSize = Size(80, 50)
            return FrameBuilderCollection(
                AnsiTitleFrameBuilder(gridStringBuilder, frameSize),
                AnsiAboutFrameBuilder(gridStringBuilder, frameSize),
                AnsiHelpFrameBuilder(gridStringBuilder, frameSize),
                AnsiTransitionFrameBuilder(gridStringBuilder, frameSize),
                AnsiCompletionFrameBuilder(gridStringBuilder, frameSize),
                AnsiGameOverFrameBuilder(gridStringBuilder, frameSize),
                AnsiConversationFrameBuilder(gridStringBuilder, frameSize),
                AnsiSceneFrameBuilder(gridStringBuilder, AnsiRoomMapBuilder(), frameSize),
                AnsiRegionMapFrameBuilder(gridStringBuilder, AnsiRegionMapBuilder(), frameSize)
            )
        }
}
