package com.github.manerajona.reactive.core.repository

import com.github.manerajona.reactive.core.model.Planet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PlanetRepo {
    fun findOne(id: Long): Mono<Planet>
    fun findAll(): Flux<Planet>
}
