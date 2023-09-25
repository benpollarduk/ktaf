package app.io

import ktaf.helpers.newline
import ktaf.io.AdjustInput
import ktaf.io.ClearOutput
import ktaf.io.DisplayTextOutput
import ktaf.io.IOConfiguration
import ktaf.io.WaitForAcknowledge
import ktaf.io.WaitForCommand
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.FrameBuilderCollection
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import ktaf.rendering.frames.ansi.AnsiRegionMapBuilder
import ktaf.rendering.frames.ansi.AnsiRegionMapFrameBuilder
import ktaf.rendering.frames.ansi.AnsiRoomMapBuilder
import ktaf.rendering.frames.ansi.AnsiSceneFrameBuilder
import ktaf.rendering.frames.html.HtmlAboutFrameBuilder
import ktaf.rendering.frames.html.HtmlCompletionFrameBuilder
import ktaf.rendering.frames.html.HtmlConversationFrameBuilder
import ktaf.rendering.frames.html.HtmlGameOverFrameBuilder
import ktaf.rendering.frames.html.HtmlHelpFrameBuilder
import ktaf.rendering.frames.html.HtmlPageBuilder
import ktaf.rendering.frames.html.HtmlTitleFrameBuilder
import ktaf.rendering.frames.html.HtmlTransitionFrameBuilder
import java.util.concurrent.locks.ReentrantLock
import javax.swing.JLabel
import javax.swing.SwingUtilities

/**
 * Provides an [IOConfiguration] for Swing with A specified [output] controls.
 */
internal class SwingConfiguration(
    private val output: JLabel,
    private val allowInputChangedListener: AllowInputChangedListener? = null
) : IOConfiguration {
    private var command: String? = ""
    private var acknowledgementReceived: Boolean? = null
    private val lock = ReentrantLock()

    /**
     * Acknowledge.
     */
    internal fun acknowledge() {
        try {
            lock.lock()
            acknowledgementReceived = true
        } finally {
            lock.unlock()
        }
    }

    /**
     * Accept a [command].
     */
    internal fun acceptCommand(command: String) {
        try {
            lock.lock()
            this.command = String(command.toCharArray())
        } finally {
            lock.unlock()
        }
    }

    override val displayTextOutput: DisplayTextOutput
        get() = object : DisplayTextOutput {
            override fun invoke(value: String) {
                // get newline
                val newline = newline()
                // convert to HTML, replace newlines with breaks
                val formatted = value.replace(newline, "<br>", true)

                val runnable = Runnable { output.text = "<html>$formatted</html>" }
                SwingUtilities.invokeLater(runnable)
            }
        }
    override val waitForAcknowledge: WaitForAcknowledge
        get() = object : WaitForAcknowledge {
            override fun invoke(): Boolean {
                while (true) {
                    try {
                        lock.lock()
                        if (acknowledgementReceived == true) {
                            acknowledgementReceived = null
                            return true
                        } else if (acknowledgementReceived == false) {
                            acknowledgementReceived = null
                            return false
                        }
                    } finally {
                        lock.unlock()
                    }
                }
            }
        }
    override val waitForCommand: WaitForCommand
        get() = object : WaitForCommand {
            override fun invoke(): String {
                var captured: String? = null
                while (captured.isNullOrEmpty()) {
                    try {
                        lock.lock()
                        val buffer: CharArray = command?.toCharArray() ?: CharArray(0)
                        if (buffer.isNotEmpty()) {
                            captured = String(buffer)
                            command = null
                        }
                    } finally {
                        lock.unlock()
                    }
                }
                return captured
            }
        }
    override val clearOutput: ClearOutput
        get() = object : ClearOutput {
            override fun invoke() {
                // clear output
                output.text = ""
            }
        }
    override val adjustInput: AdjustInput
        get() = object : AdjustInput {
            override fun invoke(allowInput: Boolean, cursorPosition: FramePosition) {
                allowInputChangedListener?.invoke(allowInput)
            }
        }
    override val frameBuilders: FrameBuilderCollection
        get() {
            val gridStringBuilder = AnsiGridStringBuilder()
            val htmlBuilder = HtmlPageBuilder()
            return FrameBuilderCollection(
                HtmlTitleFrameBuilder(htmlBuilder),
                HtmlAboutFrameBuilder(htmlBuilder),
                HtmlHelpFrameBuilder(htmlBuilder),
                HtmlTransitionFrameBuilder(htmlBuilder),
                HtmlCompletionFrameBuilder(htmlBuilder),
                HtmlGameOverFrameBuilder(htmlBuilder),
                HtmlConversationFrameBuilder(htmlBuilder),
                AnsiSceneFrameBuilder(gridStringBuilder, AnsiRoomMapBuilder()),
                AnsiRegionMapFrameBuilder(gridStringBuilder, AnsiRegionMapBuilder())
            )
        }

    internal interface AllowInputChangedListener {
        fun invoke(allowInput: Boolean)
    }
}
