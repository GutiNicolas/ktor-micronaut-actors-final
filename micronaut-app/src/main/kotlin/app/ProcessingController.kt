package app

import io.micronaut.http.annotation.*
import io.micronaut.http.*
import kotlinx.coroutines.runBlocking
import app.*

@Controller("/process")
class ProcessingController(private val actor: ProcessorActor) {

    init {
        println("🔥 Loaded Processing Conroller")
    }

    @Post
    fun procesar(@Body request: Request): Result {
        return runBlocking {
            actor.process(request)
        }
    }
}