package com.github.manerajona.reactive.ports.input.rs.handler

import com.github.manerajona.reactive.config.exception.ErrorDetails
import com.github.manerajona.reactive.config.exception.ErrorDetailsException
import com.github.manerajona.reactive.core.model.Jedi
import com.github.manerajona.reactive.core.usecase.JediService
import com.github.manerajona.reactive.ports.input.rs.mapper.JediHandlerMapper
import com.github.manerajona.reactive.ports.input.rs.request.JediRequest
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.BindingResult
import org.springframework.validation.Validator
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors

@Component
class JediHandlerV1(val service: JediService, val mapper: JediHandlerMapper, val validator: Validator) {

    private val logger = KotlinLogging.logger {}

    fun createJedis(request: ServerRequest): Mono<ServerResponse> {
        val mono: Mono<Jedi> = request.bodyToMono(JediRequest::class.java)
            .doOnNext { validate(it) }
            .map { mapper.createJediRequestToJedi(it) }

        return mono.flatMap<Any>(service::createEntity)
            .flatMap { id ->
                val location = UriComponentsBuilder
                    .fromPath(request.path() + "/{id}")
                    .build()
                    .expand(id)
                    .toUri()

                ServerResponse.created(location).build()
            }.doOnError { error -> logger.error(error.message, error) }
    }

    fun getJedi(request: ServerRequest): Mono<ServerResponse> {
        val id = UUID.fromString(request.pathVariable("id").trim { it <= ' ' })

        return service.getByIdIfExists(id)
            .flatMap { ServerResponse.ok().bodyValue(it) }
            .switchIfEmpty(ServerResponse.notFound().build())
            .doOnError { error -> logger.error(error.message, error) }
    }

    fun getJedis(ignored: ServerRequest?): Mono<ServerResponse> =
        ServerResponse.ok().body(service.list, Jedi::class.java)
            .doOnError { error -> logger.error(error.message, error) }

    fun updateJedi(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(JediRequest::class.java)
            .doOnNext { validate(it) }
            .flatMap { jediRequest ->
                val jedi: Jedi = mapper.createJediRequestToJedi(jediRequest)
                val id = UUID.fromString(request.pathVariable("id").trim { it <= ' ' })
                service.updateEntityIfExists(id, jedi)
            }.flatMap { jedi ->
                Optional.ofNullable(jedi.id)
                    .map { ServerResponse.noContent().build() }
                    .orElse(ServerResponse.notFound().build())
            }.doOnError { error -> logger.error(error.message, error) }

    fun deleteJedi(request: ServerRequest): Mono<ServerResponse> {
        val id = UUID.fromString(request.pathVariable("id").trim { it <= ' ' })

        return service.deleteById(id)
            .flatMap { ServerResponse.noContent().build() }
            .switchIfEmpty(ServerResponse.noContent().build())
            .doOnError { error -> logger.error(error.message, error) }
    }

    private fun <T> validate(dto: T) {
        val result: BindingResult = BeanPropertyBindingResult(dto, dto!!::class.simpleName ?: "")

        validator.validate(dto, result)

        if (result.hasErrors()) {
            val errors: List<ErrorDetails> = result.fieldErrors.stream()
                .map { fieldError ->
                    ErrorDetails(
                        ErrorDetails.Enums.ErrorCode.INVALID_FIELD_VALUE,
                        fieldError.defaultMessage ?: ErrorDetails.Enums.ErrorCode.INVALID_FIELD_VALUE.defaultMessage,
                        fieldError.field,
                        fieldError.rejectedValue,
                        ErrorDetails.Enums.ErrorLocation.BODY
                    )
                }
                .collect(Collectors.toList())
            throw ErrorDetailsException(HttpStatus.BAD_REQUEST, errors)
        }
    }

    companion object Constants {
        const val JEDIS_URI = "/v1/jedis"
        const val JEDIS_ID_URI = "$JEDIS_URI/{id}"
    }
}
