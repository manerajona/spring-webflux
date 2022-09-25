package com.github.manerajona.reactive.core.usecase

import com.github.manerajona.reactive.core.model.Planet
import com.github.manerajona.reactive.core.repository.PlanetRepo
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PlanetServiceImpl(val repository: PlanetRepo) : PlanetService {

    override fun getByIdIfExists(id: Long): Mono<Planet> = repository.findOne(id)

    override val list: Flux<Planet> = repository.findAll()
}
