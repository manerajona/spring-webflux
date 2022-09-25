package com.github.manerajona.reactive.core.usecase

import com.github.manerajona.reactive.core.model.Jedi
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface JediService {
    fun createEntity(entity: Jedi): Mono<UUID>
    fun deleteById(id: UUID): Mono<Void>
    fun getByIdIfExists(id: UUID): Mono<Jedi>
    val list: Flux<Jedi>
    fun updateEntityIfExists(id: UUID, entity: Jedi): Mono<Jedi>
}
