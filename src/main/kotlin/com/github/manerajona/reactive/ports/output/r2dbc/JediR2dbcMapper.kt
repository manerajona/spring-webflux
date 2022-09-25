package com.github.manerajona.reactive.ports.output.r2dbc

import com.github.manerajona.reactive.core.model.Jedi
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface JediR2dbcMapper {
    @Mapping(target = "id", ignore = true)
    fun jediToJediTable(jedi: Jedi): JediTable
    fun jediTableToJedi(jedi: JediTable): Jedi
}
