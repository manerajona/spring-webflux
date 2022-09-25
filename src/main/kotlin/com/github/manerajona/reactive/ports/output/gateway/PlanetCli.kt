package com.github.manerajona.reactive.ports.output.gateway

import com.github.manerajona.reactive.config.exception.ErrorDetails
import com.github.manerajona.reactive.config.exception.ErrorDetailsException
import com.github.manerajona.reactive.core.model.Planet
import com.github.manerajona.reactive.core.repository.PlanetRepo
import com.github.manerajona.reactive.ports.output.gateway.dto.PlanetDto
import com.github.manerajona.reactive.ports.output.gateway.dto.SwapiListResponseDto
import com.github.manerajona.reactive.ports.output.gateway.dto.SwapiSingleResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class PlanetCli(val webClient: WebClient, var mapper: PlanetCliMapper) : PlanetRepo {

    @Value("\${swapi.dev.planets.uri}")
    private lateinit var uri: String

    override fun findOne(id: Long): Mono<Planet> = webClient.get()
        .uri { builder: UriBuilder -> builder.path("$uri/{id}").build(id) }
        .retrieve()
        .bodyToMono(SwapiSingleResponseDto::class.java)
        .map { response ->
            val planetDto: PlanetDto? = response.result?.properties
            if (planetDto == null || "unknown" == planetDto.name) {
                throw ErrorDetailsException(
                    HttpStatus.NOT_FOUND,
                    listOf(
                        ErrorDetails(
                            ErrorDetails.Enums.ErrorCode.RESOURCE_NOT_FOUND,
                            "Planet not found"
                        )
                    )
                )
            }
            mapper.planetDtoToPlanet(planetDto)
        }

    override fun findAll(): Flux<Planet> = webClient.get()
        .uri { builder: UriBuilder -> builder.path(uri).build() }
        .retrieve()
        .bodyToMono(SwapiListResponseDto::class.java)
        .flatMapMany { response ->
            val planets: List<Planet> = Optional.ofNullable(response.results)
                .map(mapper::resultDtoListToPlanetList)
                .orElse(ArrayList<Planet>())
            Flux.fromStream(planets.stream())
        }

}
