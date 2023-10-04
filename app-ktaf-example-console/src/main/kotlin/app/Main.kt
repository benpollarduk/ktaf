package app

import example.ExampleGame
import ktaf.extensions.tryParseInt
import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.logic.GameExecutor
import ktaf.logic.discovery.CatalogEntry
import ktaf.logic.discovery.GameCatalogResolver
import ktaf.utilities.templates.GameTemplate
import java.io.File
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    private fun handleInvalidPath(path: String): GameTemplate {
        logger.error("Invalid path: $path.")
        println("Invalid path. Only .jar files are compatible.")
        println("ExampleGame will be used.")
        readln()
        return ExampleGame
    }

    private fun handleNoTemplateInJar(path: String): GameTemplate {
        logger.error("No GameTemplates were found in $path.")
        println("No game templates found. Game templates must be subclasses of GameTemplate.")
        println("ExampleGame will be used.")
        readln()
        return ExampleGame
    }

    private fun handleMultipleTemplatesInJar(templates: List<CatalogEntry<GameTemplate>>): GameTemplate {
        println("Found multiple games, select a number to load that game:")
        for (i in templates.indices) {
            println("${i + 1}: ${templates[i].name}")
        }

        while (true) {
            val selection = readln()
            val selectedIndex = selection.tryParseInt()
            if (selectedIndex != null) {
                val correctedIndex = selectedIndex - 1
                if (correctedIndex >= 0 && correctedIndex < templates.size) {
                    return templates[correctedIndex].template
                }
            }
        }
    }

    private fun handleGameTemplateSelection(path: String): GameTemplate {
        return if (path.isNotEmpty()) {
            // can only be a .jar file
            if (!path.endsWith(".jar", true)) {
                return handleInvalidPath(path)
            } else {
                // load game templates from file
                val catalog = GameCatalogResolver.resolveCatalogFromJar(File(path))
                val templates = catalog.get()
                return when (templates.size) {
                    0 -> handleNoTemplateInJar(path)
                    1 -> templates.first().template
                    else -> handleMultipleTemplatesInJar(templates)
                }
            }
        } else {
            ExampleGame
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val gameTemplate = handleGameTemplateSelection(args.firstOrNull() ?: "")
        logger.info("Beginning execution of $gameTemplate...")
        GameExecutor.execute(gameTemplate, ioConfiguration = AnsiConsoleConfiguration)
        logger.info("Ended execution of $gameTemplate.")
    }
}
