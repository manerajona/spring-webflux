package com.github.manerajona.reactive.ports.input.rs.mapper

import com.github.manerajona.reactive.core.model.Jedi
import com.github.manerajona.reactive.ports.input.rs.request.JediRequest
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface JediHandlerMapper {
    fun createJediRequestToJedi(request: JediRequest): Jedi
}
