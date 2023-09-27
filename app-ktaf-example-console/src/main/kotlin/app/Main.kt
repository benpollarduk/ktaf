package app

import ktaf.helpers.DebugHelper
import ktaf.logic.Game

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new example game and begin execution
        Game.execute(DebugHelper.getSimpleGameCreator())
    }
}
