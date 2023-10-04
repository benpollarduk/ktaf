package app

import app.io.KtorConfiguration
import app.plugins.configureRouting
import example.ExampleGame
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ktaf.logic.GameExecutor
import org.apache.logging.log4j.kotlin.Logging

/**
 * Provides a central logger.
 */
internal object Central : Logging

fun main() {
    Central.logger.info("Beginning async execution of $ExampleGame...")
    // create and start game on background thread
    GameExecutor.executeAysnc(ExampleGame, ioConfiguration = KtorConfiguration)

    Central.logger.info("Beginning netty server on 0.0.0.0:8080...")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
    Central.logger.info("Finished running netty server.")
}

fun Application.module() {
    configureRouting()
}
