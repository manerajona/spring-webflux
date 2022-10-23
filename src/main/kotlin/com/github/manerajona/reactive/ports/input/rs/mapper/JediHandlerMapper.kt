package com.github.manerajona.reactive.ports.input.rs.mapper

import com.github.manerajona.reactive.core.model.Jedi
import com.github.manerajona.reactive.ports.input.rs.request.JediRequest
import com.github.manerajona.reactive.ports.input.rs.response.JediResponse
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface JediHandlerMapper {
    fun jediRequestToJedi(request: JediRequest): Jedi
    fun jediToJediResponse(entity: Jedi): JediResponse
}
