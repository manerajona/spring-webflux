package com.github.manerajona.reactive.ports.output.r2dbc

import com.github.manerajona.reactive.core.model.Jedi
import com.github.manerajona.reactive.core.repository.JediRepo
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Profile("h2")
@Component
class JediR2dbcDao(val repository: JediR2dbcRepo, val mapper: JediR2dbcMapper) : JediRepo {

    @Transactional
    override fun create(jedi: Jedi): Mono<UUID> =
        repository.save(mapper.jediToJediTable(jedi))
            .mapNotNull(JediTable::id)


    @Transactional(readOnly = true)
    override fun findOne(id: UUID): Mono<Jedi> =
        repository.findById(id)
            .map(mapper::jediTableToJedi)

    @Transactional(readOnly = true)
    override fun findAll(): Flux<Jedi> =
        repository.findAll()
            .map(mapper::jediTableToJedi)

    @Transactional
    override fun update(id: UUID, jedi: Jedi): Mono<Jedi> =
        repository.findById(id)
            .defaultIfEmpty(JediTable())
            .map { jediTable ->
                jediTable.gender = jedi.gender
                jediTable.birthYear = jedi.birthYear
                jediTable.planet = jedi.planet
                jediTable.url = jedi.url
                jediTable
            }.flatMap { updated ->
                Optional.ofNullable(updated.id)
                    .map { repository.save(updated) }
                    .orElse(Mono.just(updated))
            }
            .map(mapper::jediTableToJedi)

    @Transactional
    override fun delete(id: UUID): Mono<Void> = repository.deleteById(id)
}
