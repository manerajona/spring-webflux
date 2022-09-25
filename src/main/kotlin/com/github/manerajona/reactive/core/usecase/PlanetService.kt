package com.github.manerajona.reactive.core.usecase

import com.github.manerajona.reactive.core.model.Planet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PlanetService {
    fun getByIdIfExists(id: Long): Mono<Planet>
    val list: Flux<Planet>
}
