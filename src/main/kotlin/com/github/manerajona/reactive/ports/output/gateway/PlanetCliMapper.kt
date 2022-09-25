package com.github.manerajona.reactive.ports.output.gateway

import com.github.manerajona.reactive.core.model.Planet
import com.github.manerajona.reactive.ports.output.gateway.dto.PlanetDto
import com.github.manerajona.reactive.ports.output.gateway.dto.ResultDto
import org.mapstruct.IterableMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface PlanetCliMapper {

    fun planetDtoToPlanet(response: PlanetDto): Planet

    @IterableMapping(qualifiedByName = ["resultDtoToPlanet"])
    fun resultDtoListToPlanetList(response: List<ResultDto>): List<Planet>

    @Named("resultDtoToPlanet")
    @Mapping(target = "id", source = "uid")
    fun resultDtoToPlanet(result: ResultDto): Planet
}
