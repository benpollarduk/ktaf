package com.github.benpollarduk.ktaf.ktor.io

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
import java.util.concurrent.locks.ReentrantLock

/**
 * Provides an [IOConfiguration] for a Ktor application.
 */
public object KtorConfiguration : IOConfiguration {
    private var command: String? = null
    private var acknowledgementReceived: Boolean? = null
    private val lock = ReentrantLock()
    private var lastFrame: String = ""
    private var canAcceptCommand: Boolean = false
    private var hasFrameArrived: Boolean = false

    /**
     * Get the last received frame as an HTML string.
     */
    public fun getLastFrame(): String {
        try {
            lock.lock()
            hasFrameArrived = false
            return String(lastFrame.toCharArray())
        } finally {
            lock.unlock()
        }
    }

    /**
     * Get if a new frame has arrived.
     */
    public fun getHasFrameArrived(): Boolean {
        try {
            lock.lock()
            return hasFrameArrived
        } finally {
            lock.unlock()
        }
    }

    /**
     * Determine if the game can accept a command.
     */
    public fun canAcceptCommand(): Boolean {
        try {
            lock.lock()
            return canAcceptCommand
        } finally {
            lock.unlock()
        }
    }

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
            KtorConfiguration.command = String(command.toCharArray())
        } finally {
            lock.unlock()
        }
    }

    override val renderFrame: RenderFrame
        get() = object : RenderFrame {
            override fun invoke(frame: String, allowInput: Boolean, cursorPosition: FramePosition) {
                try {
                    lock.lock()
                    // pull default HTML from resources
                    lastFrame =
                        """
                            <!DOCTYPE html>
                            <html>
                            <head>
                                <title>app-ktaf-example-ktor</title>
                                <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
                            </head>
                            <body>
                            $frame
                            <form id="inputForm">
                                <input class="input-command" type="text" id="command" name="command">
                            </form>
                            <script src="/static/js/script.js"></script>
                            </body>
                            </html>
                        """.trimIndent()
                    hasFrameArrived = true
                    canAcceptCommand = allowInput
                } finally {
                    lock.unlock()
                }
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
            val mapSize = Size(60, 35)
            val htmlBuilder = HtmlPageBuilder(HtmlElementType.Div)
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
}
