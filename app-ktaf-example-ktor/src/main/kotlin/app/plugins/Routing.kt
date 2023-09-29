package app.plugins

import app.io.KtorConfiguration
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay

fun Application.configureRouting() {
    routing {
        get("/") {
            when (val command = call.parameters["command"]) {
                null -> Unit
                else -> {
                    if (KtorConfiguration.canAcceptCommand()) {
                        KtorConfiguration.acceptCommand(command)
                    } else {
                        KtorConfiguration.acknowledge()
                    }
                }
            }

            // capture current time
            val startTime = System.currentTimeMillis()
            val timeoutInMs = 1000
            var timedOut = false

            // allow the game time to respond and publish a new frame
            while (!timedOut) {
                if (KtorConfiguration.getHasFrameArrived()) {
                    break
                } else {
                    timedOut = System.currentTimeMillis() - startTime >= timeoutInMs

                    if (timedOut) {
                        delay(10)
                    }
                }
            }

            if (timedOut) {
                call.respondText("Timed out.", contentType = ContentType.Text.Plain)
            } else {
                call.respondText(KtorConfiguration.getLastFrame(), contentType = ContentType.Text.Html)
            }
        }
    }
    routing {
        staticResources("static", "/static")
    }
}
