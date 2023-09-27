package app

import example.ExampleGameCreator
import ktaf.io.configurations.AnsiConsoleConfiguration
import ktaf.logic.Game

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new example game and begin execution
        Game.execute(ExampleGameCreator.create(AnsiConsoleConfiguration))
    }
}
