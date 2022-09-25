package com.github.manerajona.reactive.ports.output.mongo

import com.github.manerajona.reactive.core.model.Jedi
import com.github.manerajona.reactive.core.repository.JediRepo
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Profile("!h2")
@Component
class JediMongoDao(val repository: JediMongoRepo, val mapper: JediMongoMapper) : JediRepo {

    override fun create(jedi: Jedi): Mono<UUID> =
        repository.save(mapper.jediToJediDocument(jedi))
            .mapNotNull(JediDocument::id)

    override fun findOne(id: UUID): Mono<Jedi> =
        repository.findById(id)
            .map(mapper::jediDocumentToJedi)

    override fun findAll(): Flux<Jedi> =
        repository.findAll()
            .map(mapper::jediDocumentToJedi)

    override fun update(id: UUID, jedi: Jedi): Mono<Jedi> =
        repository.findById(id)
            .defaultIfEmpty(JediDocument())
            .map { jediMongo ->
                jediMongo.gender = jedi.gender
                jediMongo.birthYear = jedi.birthYear
                jediMongo.planet = jedi.planet
                jediMongo.url = jedi.url
                jediMongo
            }.flatMap { updated ->
                Optional.ofNullable(updated.id)
                    .map { repository.save(updated) }
                    .orElse(Mono.just(updated))
            }
            .map(mapper::jediDocumentToJedi)

    override fun delete(id: UUID): Mono<Void> =
        repository.deleteById(id)
}
