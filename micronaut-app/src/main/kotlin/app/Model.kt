package app

import io.micronaut.serde.annotation.Serdeable
import kotlinx.coroutines.channels.SendChannel

@Serdeable
data class Request(val input: String)

@Serdeable
data class Result(val output: String, val fromMessage: String)

data class RequestMessage(val request: Request, val replyTo: SendChannel<Result>)