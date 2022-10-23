package com.github.manerajona.reactive.config.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.manerajona.reactive.config.exception.ErrorDetails.Enums
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.data.util.Pair
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Order(-2)
class GlobalExceptionHandler(
    private val mapper: ObjectMapper
) : ErrorWebExceptionHandler {

    override fun handle(
        serverWebExchange: ServerWebExchange,
        throwable: Throwable
    ): Mono<Void> {
        val pair: Pair<HttpStatus, List<ErrorDetails>> =
            if (throwable is ErrorDetailsException) {
                Pair.of(throwable.status, throwable.errors)
            } else {
                Pair.of(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    listOf(
                        ErrorDetails(
                            Enums.ErrorCode.INTERNAL_ERROR,
                            Enums.ErrorCode.INTERNAL_ERROR.defaultMessage
                        )
                    )
                )
            }

        serverWebExchange.response.statusCode = pair.first
        serverWebExchange.response.headers.contentType = MediaType.APPLICATION_JSON

        return serverWebExchange.response.writeWith(
            getBody(
                serverWebExchange,
                pair.second // list of errors
            )
        )
    }

    private fun getBody(
        serverWebExchange: ServerWebExchange,
        errors: List<ErrorDetails>
    ): Mono<DataBuffer> {
        val dataBuffer = serverWebExchange.response
            .bufferFactory()
            .wrap(mapper.writeValueAsBytes(errors))

        return Mono.just(dataBuffer)
    }
}
