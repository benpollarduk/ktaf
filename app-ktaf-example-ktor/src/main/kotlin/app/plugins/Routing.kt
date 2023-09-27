package app.plugins

import app.io.KtorConfiguration
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay

fun Application.configureRouting() {
    routing {
        get("/") {
            when (val command = call.parameters["command"]) {
                null -> Unit
                "" -> {
                    KtorConfiguration.acknowledge()
                }
                else -> {
                    if (KtorConfiguration.canAcceptCommand()) {
                        KtorConfiguration.acceptCommand(command)
                    } else {
                        KtorConfiguration.acknowledge()
                    }
                }
            }
            // allow the game time to respond and publish a new frame... not ideal, ideally this should be delegated
            // to the KtorConfiguration to update when the frame arrives
            delay(50)
            call.respondText(KtorConfiguration.getLastFrame(), contentType = ContentType.Text.Html)
        }
    }
}
