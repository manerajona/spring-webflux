package com.github.manerajona.reactive.core.repository

import com.github.manerajona.reactive.core.model.Jedi
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface JediRepo {
    fun create(jedi: Jedi): Mono<UUID>
    fun findOne(id: UUID): Mono<Jedi>
    fun update(id: UUID, jedi: Jedi): Mono<Jedi>
    fun findAll(): Flux<Jedi>
    fun delete(id: UUID): Mono<Void>
}
