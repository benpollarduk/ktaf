package app

import app.io.SwingConfiguration
import ktaf.helpers.DebugHelper
import ktaf.logic.Game
import ktaf.logic.GameCreator
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingUtilities

class GameApp : JFrame("app-example-swing") {
    private var currentFrameAcceptsInput: Boolean = false
    init {
        // set up main frame
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setSize(600, 800)
        this.layout = BorderLayout()

        // create components
        val output = JLabel()
        val input = JTextField()
        val outputPanel = JPanel()
        val inputPanel = JPanel()
        val allowInputListener = object : SwingConfiguration.AllowInputChangedListener {
            override fun invoke(allowInput: Boolean) {
                currentFrameAcceptsInput = allowInput
            }
        }
        val ioConfiguration = SwingConfiguration(output, allowInputListener)

        // configure output UI
        outputPanel.layout = BorderLayout()
        outputPanel.background = Color.BLACK
        output.font = Font("Consolas", Font.PLAIN, 11)
        output.foreground = Color.WHITE

        // configure input UI
        inputPanel.layout = BorderLayout()
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

        // arrange
        outputPanel.add(output)
        inputPanel.add(input)
        this.add(outputPanel, BorderLayout.CENTER)
        this.add(inputPanel, BorderLayout.SOUTH)

        this.isVisible = true

        // create and start game on background thread
        val game = DebugHelper.getSimpleGameCreator(ioConfiguration)
        val gameThread = Thread(GameLogic(game))
        gameThread.start()
    }

    inner class GameLogic(private val gameCreator: GameCreator) : Runnable {
        override fun run() {
            Game.execute(gameCreator)
        }
    }

    public companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SwingUtilities.invokeLater {
                GameApp()
            }
        }
    }
}
