package com.github.manerajona.reactive.core.usecase

import com.github.manerajona.reactive.core.model.Jedi
import com.github.manerajona.reactive.core.repository.JediRepo
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class JediServiceImpl(val repository: JediRepo) : JediService {

    override fun createEntity(entity: Jedi): Mono<UUID> = repository.create(entity)

    override fun getByIdIfExists(id: UUID): Mono<Jedi> = repository.findOne(id)

    override val list: Flux<Jedi> = repository.findAll()

    override fun updateEntityIfExists(id: UUID, entity: Jedi): Mono<Jedi> = repository.update(id, entity)

    override fun deleteById(id: UUID): Mono<Void> = repository.delete(id)
}
