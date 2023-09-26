package app.io

import ktaf.assets.Size
import ktaf.io.AdjustInput
import ktaf.io.ClearOutput
import ktaf.io.DisplayTextOutput
import ktaf.io.IOConfiguration
import ktaf.io.WaitForAcknowledge
import ktaf.io.WaitForCommand
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.FrameBuilderCollection
import ktaf.rendering.frames.GridRegionMapBuilder
import ktaf.rendering.frames.GridRoomMapBuilder
import ktaf.rendering.frames.ansi.AnsiGridStringBuilder
import ktaf.rendering.frames.html.HTMLAboutFrameBuilder
import ktaf.rendering.frames.html.HTMLCompletionFrameBuilder
import ktaf.rendering.frames.html.HTMLConversationFrameBuilder
import ktaf.rendering.frames.html.HTMLElementType
import ktaf.rendering.frames.html.HTMLGameOverFrameBuilder
import ktaf.rendering.frames.html.HTMLHelpFrameBuilder
import ktaf.rendering.frames.html.HTMLPageBuilder
import ktaf.rendering.frames.html.HTMLRegionMapFrameBuilder
import ktaf.rendering.frames.html.HTMLSceneFrameBuilder
import ktaf.rendering.frames.html.HTMLTitleFrameBuilder
import ktaf.rendering.frames.html.HTMLTransitionFrameBuilder
import java.util.concurrent.locks.ReentrantLock
import javax.swing.JEditorPane
import javax.swing.SwingUtilities

/**
 * Provides an [IOConfiguration] for Swing with A specified [output] controls.
 */
internal class SwingConfiguration(
    private val output: JEditorPane,
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
                val runnable = Runnable { output.text = value }
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
            val htmlBuilder = HTMLPageBuilder(HTMLElementType.Document("ktaf frame", mainCss))
            val frameSize = Size(80, 50)
            return FrameBuilderCollection(
                HTMLTitleFrameBuilder(htmlBuilder),
                HTMLAboutFrameBuilder(htmlBuilder),
                HTMLHelpFrameBuilder(htmlBuilder),
                HTMLTransitionFrameBuilder(htmlBuilder),
                HTMLCompletionFrameBuilder(htmlBuilder),
                HTMLGameOverFrameBuilder(htmlBuilder),
                HTMLConversationFrameBuilder(htmlBuilder),
                HTMLSceneFrameBuilder(htmlBuilder, GridRoomMapBuilder(), frameSize),
                HTMLRegionMapFrameBuilder(htmlBuilder, GridRegionMapBuilder(), frameSize)
            )
        }

    internal interface AllowInputChangedListener {
        fun invoke(allowInput: Boolean)
    }

    private companion object {
        private val mainCss: String = "body { background-color: black; font-size: 11; margin: 10px; font-family: Consolas, monospace; color: white; }"
    }
}
