package app

import app.io.KtorConfiguration
import app.plugins.configureRouting
import example.ExampleGame
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ktaf.logic.GameExecutor

fun main() {
    // create and start game on background thread
    GameExecutor.executeAysnc(ExampleGame, ioConfiguration = KtorConfiguration)

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}