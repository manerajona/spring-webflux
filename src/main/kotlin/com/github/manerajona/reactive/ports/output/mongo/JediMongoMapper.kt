package com.github.manerajona.reactive.ports.output.mongo

import com.github.manerajona.reactive.core.model.Jedi
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.util.*

@Mapper(componentModel = "spring", uses = [UUID::class])
interface JediMongoMapper {
    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    fun jediToJediDocument(jedi: Jedi): JediDocument
    fun jediDocumentToJedi(jedi: JediDocument): Jedi
}
