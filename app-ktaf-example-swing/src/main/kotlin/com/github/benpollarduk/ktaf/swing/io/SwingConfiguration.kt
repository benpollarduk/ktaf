package com.github.benpollarduk.ktaf.swing.io

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.io.IOConfiguration
import com.github.benpollarduk.ktaf.io.RenderFrame
import com.github.benpollarduk.ktaf.io.WaitForAcknowledge
import com.github.benpollarduk.ktaf.io.WaitForCommand
import com.github.benpollarduk.ktaf.logic.CancellationToken
import com.github.benpollarduk.ktaf.rendering.FramePosition
import com.github.benpollarduk.ktaf.rendering.frames.FrameBuilderCollection
import com.github.benpollarduk.ktaf.rendering.frames.GridRegionMapBuilder
import com.github.benpollarduk.ktaf.rendering.frames.GridRoomMapBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlAboutFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlCompletionFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlConversationFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlElementType
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlGameOverFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlHelpFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlPageBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlRegionMapFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlSceneFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlTitleFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.html.HtmlTransitionFrameBuilder
import com.github.benpollarduk.ktaf.swing.HtmlHelper
import java.util.concurrent.locks.ReentrantLock
import javax.swing.JEditorPane
import javax.swing.SwingUtilities

/**
 * Provides an [IOConfiguration] for a Swing application. The output [JEditorPane] must be specified [output].
 * [allowInputChangedListener] allows a listener to be injected to allow callbacks back to the UI when the adjustInput
 * method is called.
 */
internal class SwingConfiguration(
    private val output: JEditorPane,
    private val allowInputChangedListener: AllowInputChangedListener? = null,
) : IOConfiguration {
    private var command: String? = null
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

    override val renderFrame: RenderFrame
        get() = object : RenderFrame {
            override fun invoke(frame: String, allowInput: Boolean, cursorPosition: FramePosition) {
                val runnable = Runnable { output.text = frame }
                allowInputChangedListener?.invoke(allowInput)
                SwingUtilities.invokeLater(runnable)
            }
        }

    override val waitForAcknowledge: WaitForAcknowledge
        get() = object : WaitForAcknowledge {
            override fun invoke(cancellationToken: CancellationToken): Boolean {
                while (true) {
                    if (cancellationToken.wasCancelled) {
                        return false
                    }
                    try {
                        lock.lock()
                        if (acknowledgementReceived == true) {
                            acknowledgementReceived = null
                            command = null
                            return true
                        } else if (acknowledgementReceived == false) {
                            acknowledgementReceived = null
                            command = null
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
            override fun invoke(cancellationToken: CancellationToken): String {
                var captured: String? = null
                while (captured == null) {
                    if (cancellationToken.wasCancelled) {
                        return ""
                    }
                    try {
                        lock.lock()
                        val buffer: CharArray = command?.toCharArray() ?: CharArray(0)
                        if (buffer.isNotEmpty()) {
                            captured = String(buffer)
                            command = null
                        } else if (command != null) {
                            // could be empty string
                            captured = ""
                            command = null
                        }
                    } finally {
                        lock.unlock()
                    }
                }
                return captured
            }
        }

    override val frameBuilders: FrameBuilderCollection
        get() {
            val width = 600
            val mapSize = Size(60, 35)
            val htmlBuilder = HtmlPageBuilder(HtmlElementType.Document("ktaf frame", HtmlHelper.createCSS(width)))
            return FrameBuilderCollection(
                HtmlTitleFrameBuilder(htmlBuilder),
                HtmlAboutFrameBuilder(htmlBuilder),
                HtmlHelpFrameBuilder(htmlBuilder),
                HtmlTransitionFrameBuilder(htmlBuilder),
                HtmlCompletionFrameBuilder(htmlBuilder),
                HtmlGameOverFrameBuilder(htmlBuilder),
                HtmlConversationFrameBuilder(htmlBuilder),
                HtmlSceneFrameBuilder(htmlBuilder, GridRoomMapBuilder(), mapSize),
                HtmlRegionMapFrameBuilder(htmlBuilder, GridRegionMapBuilder(), mapSize),
            )
        }

    internal interface AllowInputChangedListener {
        fun invoke(allowInput: Boolean)
    }
}
