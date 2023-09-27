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
public object KtorConfiguration: IOConfiguration {
    private var command: String? = ""
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

    override val displayTextOutput: DisplayTextOutput
        get() = object : DisplayTextOutput {
            override fun invoke(value: String) {
                try {
                    lock.lock()
                    lastFrame = getFullyFormedHTML(value)
                    hasFrameArrived = true
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

    private fun getFullyFormedHTML(div: String): String {
        return """
            <!DOCTYPE html>
            <html>
                <head>
                    <title>app-ktaf-example-ktor</title>
                    <style>
                    body 
                    {
                        background-color: black;
                        font-size: 12px;
                        font-family: Consolas, monospace;
                        color: white;
                        word-wrap: break-word;
                        width:100%;
                        max-width: 600px;
                    }
                    div {
                        margin: 10px;
                    }
                    .input-command 
                    {
                        font-size: 12px;
                        font-family: Consolas, monospace;
                        margin: 10px;
                        width:100%;
                        max-width: 600px;
                    }
                    </style>
                </head>
                <body>
                    $div
                    <form id="inputForm">
                        <input class="input-command" type="text" id="command" name="command">
                    </form>
                    <script>
                        const commandInput = document.getElementById('command');
                        const form = document.getElementById('inputForm');
    
                        // enter trigger submit
                        commandInput.addEventListener('keydown', function(event) {
                            if (event.key === 'Enter') {
                                event.preventDefault();
                                form.submit();
                            }
                        });
                        
                        // wait for the page to fully load then give focus to input
                        window.addEventListener('load', function() {
                            const inputElement = document.getElementById('command');
                            inputElement.focus();
                        });
                    </script>
                </body>
            </html>
        """.trimIndent()
    }
}
