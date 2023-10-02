package app

import example.ExampleGameCreator
import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.logic.GameExecutor

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new example game and begin execution
        GameExecutor.execute(ExampleGameCreator.create(AnsiConsoleConfiguration))
    }
}
