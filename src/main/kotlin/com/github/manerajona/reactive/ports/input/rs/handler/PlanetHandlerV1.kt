package com.github.manerajona.reactive.ports.input.rs.handler

import com.github.manerajona.reactive.core.model.Planet
import com.github.manerajona.reactive.core.usecase.PlanetService
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class PlanetHandlerV1(val service: PlanetService) {

    private val logger = KotlinLogging.logger {}

    fun getPlanet(request: ServerRequest): Mono<ServerResponse> {
        val id = java.lang.Long.valueOf(request.pathVariable("id"))

        return service.getByIdIfExists(id)
            .flatMap { ServerResponse.ok().bodyValue(it) }
            .switchIfEmpty(ServerResponse.notFound().build())
            .doOnError { error -> logger.error(error.message, error) }
    }

    fun getPlanets(ignored: ServerRequest?): Mono<ServerResponse> =
        ServerResponse.ok().body(service.list, Planet::class.java)
            .doOnError { error -> logger.error(error.message, error) }

    companion object Constants {
        const val PLANET_URI = "/v1/planets"
        const val PLANET_ID_URI = "$PLANET_URI/{id}"
    }
}
