package app

import app.io.KtorConfiguration
import app.plugins.configureRouting
import example.ExampleGameCreator
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ktaf.logic.Game
import ktaf.logic.GameCreator

fun main() {
    // create and start game on background thread
    val game = ExampleGameCreator.create(KtorConfiguration)
    val gameThread = Thread(GameLogic(game))
    gameThread.start()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}

class GameLogic(private val gameCreator: GameCreator) : Runnable {
    override fun run() {
        Game.execute(gameCreator)
    }
}
