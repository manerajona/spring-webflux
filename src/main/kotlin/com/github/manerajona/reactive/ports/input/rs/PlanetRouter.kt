package com.github.manerajona.reactive.ports.input.rs

import com.github.manerajona.reactive.ports.input.rs.handler.PlanetHandlerV1
import com.github.manerajona.reactive.ports.input.rs.response.PlanetResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class PlanetRouter {

    @RouterOperations(
        RouterOperation(
            path = PlanetHandlerV1.PLANET_URI,
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "getPlanets",
                responses = [ApiResponse(
                    responseCode = "200",
                    content = [Content(schema = Schema(implementation = PlanetResponse::class))]
                )]
            )
        ),
        RouterOperation(
            path = PlanetHandlerV1.PLANET_ID_URI,
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "getPlanet",
                parameters = [Parameter(name = "id", `in` = ParameterIn.PATH)],
                responses = [ApiResponse(
                    responseCode = "200",
                    content = [Content(schema = Schema(implementation = PlanetResponse::class))]
                )]
            )
        )
    )
    @Bean
    fun planetRoutes(handler: PlanetHandlerV1): RouterFunction<ServerResponse> = RouterFunctions.route()
        .GET(
            PlanetHandlerV1.PLANET_URI, accept(MediaType.APPLICATION_JSON)
        ) { request -> handler.getPlanets(request) }
        .GET(
            PlanetHandlerV1.PLANET_ID_URI, accept(MediaType.APPLICATION_JSON)
        ) { request -> handler.getPlanet(request) }
        .build()
}
