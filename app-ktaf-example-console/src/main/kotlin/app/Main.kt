package app

import example.ExampleGame
import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.logic.GameExecutor
import ktaf.logic.discovery.GameCatalogResolver
import ktaf.utilities.templates.GameTemplate
import java.io.File

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        var gameTemplate: GameTemplate? = null

        if (args.count() == 1) {
            val path = args.first()
            // can only be a .jar file
            if (!path.endsWith(".jar", true)) {
                println("Invalid path. Only .jar files are compatible.")
            } else {
                // load game templates from file
                val catalog = GameCatalogResolver.resolveCatalogFromJar(File(path))
                val templates = catalog.get()
                if (templates.any()) {
                    gameTemplate = templates.first().template
                } else {
                    println("No game templates found. Game templates must be subclasses of GameTemplate.")
                }
            }
        } else {
            gameTemplate = ExampleGame
        }

        if (gameTemplate != null) {
            // begin execution of the game
            GameExecutor.execute(gameTemplate, ioConfiguration = AnsiConsoleConfiguration)
        }
    }
}
