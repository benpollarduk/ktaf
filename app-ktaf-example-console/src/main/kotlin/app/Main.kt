package app

import ktaf.helpers.DebugHelper
import ktaf.logic.Game

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Game.execute(DebugHelper.getSimpleGameCreator())
    }
}
