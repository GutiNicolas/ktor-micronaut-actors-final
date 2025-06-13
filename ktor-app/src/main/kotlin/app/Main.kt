
package app

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        val actor = startProcessorActor()
        routing {
            post("/process") {
                val request = call.receive<Request>()
                val result = actor.sendAndReceive(request)
                call.respond(HttpStatusCode.OK, result)
            }
        }
    }.start(wait = true)
}

@Serializable data class Request(val input: String)
@Serializable data class Result(val output: String, val fromMessage: String = "default")
data class RequestMessage(val request: Request, val replyTo: SendChannel<Result>)

fun CoroutineScope.startProcessorActor(): SendChannel<RequestMessage> {
    val channel = Channel<RequestMessage>()
    launch {
        for (msg in channel) {
            val output = "Hello, from World!"
            val fromMessage = "${msg.request.input}"
            msg.replyTo.send(Result(output, fromMessage))
        }
    }
    return channel
}

suspend fun SendChannel<RequestMessage>.sendAndReceive(request: Request): Result = coroutineScope {
    val response = Channel<Result>()
    send(RequestMessage(request, response))
    response.receive()
}
