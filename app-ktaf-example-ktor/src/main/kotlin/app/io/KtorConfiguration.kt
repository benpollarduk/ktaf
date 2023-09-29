package app.io

import ktaf.assets.Size
import ktaf.io.IOConfiguration
import ktaf.io.RenderFrame
import ktaf.io.WaitForAcknowledge
import ktaf.io.WaitForCommand
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.FrameBuilderCollection
import ktaf.rendering.frames.GridRegionMapBuilder
import ktaf.rendering.frames.GridRoomMapBuilder
import ktaf.rendering.frames.html.HtmlAboutFrameBuilder
import ktaf.rendering.frames.html.HtmlCompletionFrameBuilder
import ktaf.rendering.frames.html.HtmlConversationFrameBuilder
import ktaf.rendering.frames.html.HtmlElementType
import ktaf.rendering.frames.html.HtmlGameOverFrameBuilder
import ktaf.rendering.frames.html.HtmlHelpFrameBuilder
import ktaf.rendering.frames.html.HtmlPageBuilder
import ktaf.rendering.frames.html.HtmlRegionMapFrameBuilder
import ktaf.rendering.frames.html.HtmlSceneFrameBuilder
import ktaf.rendering.frames.html.HtmlTitleFrameBuilder
import ktaf.rendering.frames.html.HtmlTransitionFrameBuilder
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
            this.command = String(command.toCharArray())
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
            override fun invoke(): Boolean {
                while (true) {
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
            override fun invoke(): String {
                var captured: String? = null
                while (captured == null) {
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
