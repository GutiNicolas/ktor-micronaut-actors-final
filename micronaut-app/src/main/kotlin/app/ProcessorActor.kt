package app

import jakarta.inject.Singleton
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

@Singleton
class ProcessorActor {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val channel = Channel<RequestMessage>()

    init {
        scope.launch {
            for (msg in channel) {
                val result = Result(output="Hello, from World!", fromMessage="${msg.request.input}")
                msg.replyTo.send(result)
            }
        }
    }

    suspend fun process(request: Request): Result = coroutineScope {
        val response = Channel<Result>()
        channel.send(RequestMessage(request, response))
        response.receive()
    }
}
