package app

import app.io.SwingConfiguration
import example.ExampleGame
import ktaf.logic.GameExecutor
import java.awt.BorderLayout
import java.awt.Color
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JEditorPane
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingUtilities
import javax.swing.border.EmptyBorder

class GameApp : JFrame("app-example-swing") {
    private var currentFrameAcceptsInput: Boolean = false
    init {
        // set up main frame
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setSize(600, 800)
        this.layout = BorderLayout()

        // create components for interacting with the game
        val output = JEditorPane()
        val input = JTextField()
        val prompt = JLabel()
        val outputPanel = JPanel()
        val inputPanel = JPanel()
        val allowInputListener = object : SwingConfiguration.AllowInputChangedListener {
            override fun invoke(allowInput: Boolean) {
                // capture if input is allowed on the current frame
                currentFrameAcceptsInput = allowInput
            }
        }
        val ioConfiguration = SwingConfiguration(output, allowInputListener)

        // configure output UI
        outputPanel.layout = BorderLayout()
        output.contentType = "text/html"
        output.isEditable = false
        output.border = null

        // configure input UI
        inputPanel.layout = BorderLayout()
        prompt.text = ">"
        prompt.background = Color.LIGHT_GRAY
        prompt.foreground = Color.BLACK
        prompt.isOpaque = true
        prompt.border = EmptyBorder(5, 10, 5, 0)
        input.background = Color.LIGHT_GRAY
        input.border = EmptyBorder(5, 10, 5, 10)
        input.requestFocusInWindow()
        input.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                // allow enter key presses to accept command input
                if (e.keyCode == KeyEvent.VK_ENTER) {
                    if (currentFrameAcceptsInput) {
                        ioConfiguration.acceptCommand(input.text)
                    } else {
                        ioConfiguration.acknowledge()
                    }

                    input.text = ""
                }
            }
        })

        // add all UI to panels
        outputPanel.add(output)
        inputPanel.add(prompt, BorderLayout.WEST)
        inputPanel.add(input, BorderLayout.CENTER)
        // add panels to this frame
        this.add(outputPanel, BorderLayout.CENTER)
        this.add(inputPanel, BorderLayout.SOUTH)

        this.isVisible = true

        // create and start game on background thread
        GameExecutor.executeAysnc(ExampleGame, ioConfiguration = ioConfiguration)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SwingUtilities.invokeLater {
                GameApp()
            }
        }
    }
}
