package app

import app.io.SwingConfiguration
import example.ExampleGame
import ktaf.io.IOConfiguration
import ktaf.logic.GameExecutor
import ktaf.logic.discovery.CatalogEntry
import ktaf.logic.discovery.GameCatalogResolver
import ktaf.utilities.templates.GameTemplate
import java.awt.BorderLayout
import java.awt.Color
import java.awt.FlowLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JEditorPane
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingUtilities
import javax.swing.border.EmptyBorder
import javax.swing.filechooser.FileNameExtensionFilter

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
        output.text = HtmlHelper.simpleMessage(
            """
                Load a GameTemplate from a .jar file or run an example.<br>
                Please only run .jar files from trusted sources.
            """,
        ).trimIndent()

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
        this.add(createMenu(ioConfiguration), BorderLayout.NORTH)
        this.add(outputPanel, BorderLayout.CENTER)
        this.add(inputPanel, BorderLayout.SOUTH)

        this.isVisible = true
    }

    private fun createMenu(ioConfiguration: IOConfiguration): JMenuBar {
        val menu = JMenuBar()
        val gameMenuItem = JMenu("Game")
        val importJarMenuItem = JMenuItem("Load .jar...")
        val examplesMenuItem = JMenu("Examples")
        val ktafDemoMenuItem = JMenuItem("ktaf-demo")

        importJarMenuItem.addActionListener {
            val fileChooser = JFileChooser()
            val jarFileFilter = FileNameExtensionFilter("JAR Files", "jar")
            fileChooser.fileFilter = jarFileFilter
            val returnValue = fileChooser.showOpenDialog(this@GameApp)
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                val file = fileChooser.selectedFile
                loadGameFromFile(file, ioConfiguration)
            }
        }

        ktafDemoMenuItem.addActionListener {
            beginGame(ExampleGame, ioConfiguration)
        }

        examplesMenuItem.add(ktafDemoMenuItem)
        gameMenuItem.add(examplesMenuItem)
        gameMenuItem.add(importJarMenuItem)
        menu.add(gameMenuItem)
        return menu
    }

    private fun loadGameFromFile(file: File, ioConfiguration: IOConfiguration) {
        val catalogEntries = GameCatalogResolver.resolveCatalogFromJar(file)
        val gameTemplates = catalogEntries.get()
        if (gameTemplates.size == 1) {
            beginGame(gameTemplates.first().template, ioConfiguration)
        } else if (gameTemplates.size > 1) {
            handleMultipleGamesFoundInJar(gameTemplates, ioConfiguration)
        } else {
            JOptionPane.showMessageDialog(
                this,
                "No games were found.",
                "No games found in ${file.name}",
                JOptionPane.INFORMATION_MESSAGE,
            )
        }
    }

    private fun handleMultipleGamesFoundInJar(
        gameTemplates: List<CatalogEntry<GameTemplate>>,
        ioConfiguration: IOConfiguration,
    ) {
        val dialog = JDialog(this, "Select Game", true)
        dialog.defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE
        dialog.layout = FlowLayout()

        gameTemplates.forEach { game ->
            val button = JButton(game.name)
            button.addActionListener {
                beginGame(game.template, ioConfiguration)
                dialog.dispose()
            }
            dialog.add(button)
        }

        dialog.pack()
        dialog.setLocationRelativeTo(this)
        dialog.isVisible = true
    }

    private fun beginGame(gameTemplate: GameTemplate, ioConfiguration: IOConfiguration) {
        // cancel any pending
        GameExecutor.cancel()

        // create and start game on background thread
        GameExecutor.executeAysnc(gameTemplate, ioConfiguration = ioConfiguration)
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
