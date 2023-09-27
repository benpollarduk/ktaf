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

/**
 * Provides an [IOConfiguration] for a Ktor application.
 */
public object KtorConfiguration: IOConfiguration {
    private var command: String? = ""
    private var acknowledgementReceived: Boolean? = null
    private val lock = ReentrantLock()
    private var lastFrame: String = ""
    private var canAcceptCommand: Boolean = false

    /**
     * Get the last received frame as a HTML string.
     */
    public fun getLastFrame() : String {
        try {
            lock.lock()
            return String(lastFrame.toCharArray())
        } finally {
            lock.unlock()
        }
    }

    /**
     * Determine if the game can accept a command.
     */
    public fun canAcceptCommand() : Boolean {
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

    override val displayTextOutput: DisplayTextOutput
        get() = object : DisplayTextOutput {
            override fun invoke(value: String) {
                try {
                    lock.lock()
                    lastFrame = getFullyFormedHTML(value)
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
            override fun invoke() = Unit
        }
    override val adjustInput: AdjustInput
        get() = object : AdjustInput {
            override fun invoke(allowInput: Boolean, cursorPosition: FramePosition) {
                try {
                    lock.lock()
                    canAcceptCommand = allowInput
                } finally {
                    lock.unlock()
                }
            }
        }
    override val frameBuilders: FrameBuilderCollection
        get() {
            val mapSize = Size(60, 35)
            val htmlBuilder = HTMLPageBuilder(HTMLElementType.Div)
            return FrameBuilderCollection(
                HTMLTitleFrameBuilder(htmlBuilder),
                HTMLAboutFrameBuilder(htmlBuilder),
                HTMLHelpFrameBuilder(htmlBuilder),
                HTMLTransitionFrameBuilder(htmlBuilder),
                HTMLCompletionFrameBuilder(htmlBuilder),
                HTMLGameOverFrameBuilder(htmlBuilder),
                HTMLConversationFrameBuilder(htmlBuilder),
                HTMLSceneFrameBuilder(htmlBuilder, GridRoomMapBuilder(), mapSize),
                HTMLRegionMapFrameBuilder(htmlBuilder, GridRegionMapBuilder(), mapSize),
            )
        }

    private fun getFullyFormedHTML(div: String) : String {
        return """
            <!DOCTYPE html>
            <html>
                <head>
                    <title>app-ktaf-example-ktor</title>
                    <style>
                    body {
                        background-color: black;
                        font-size: 10px;
                        font-family: Consolas, monospace;
                        color: white;
                        word-wrap: break-word;
                        margin: 10px;
                        width:100%;
                        max-width: 600px;
                    }
                    </style>
                </head>
                <body>
                    $div
                    <form id="inputForm">
                        <label for="name">Command:</label>
                        <input type="text" id="command" name="command">
                    </form>
                    <script>
                        const commandInput = document.getElementById('command');
                        const form = document.getElementById('inputForm');
    
                        commandInput.addEventListener('keydown', function(event) {
                            if (event.key === 'Enter') {
                                event.preventDefault();
                                form.submit();
                            }
                        });
                    </script>
                </body>
            </html>
        """.trimIndent()
    }
}
