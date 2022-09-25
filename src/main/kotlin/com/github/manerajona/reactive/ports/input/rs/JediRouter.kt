package com.github.manerajona.reactive.ports.input.rs

import com.github.manerajona.reactive.ports.input.rs.handler.JediHandlerV1
import com.github.manerajona.reactive.ports.input.rs.request.JediRequest
import com.github.manerajona.reactive.ports.input.rs.response.JediResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
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
class JediRouter {

    @RouterOperations(
        RouterOperation(
            path = JediHandlerV1.JEDIS_URI,
            method = [RequestMethod.POST],
            operation = Operation(
                operationId = "createJedis",
                requestBody = RequestBody(
                    content = [Content(schema = Schema(implementation = JediRequest::class))]
                ),
                responses = [ApiResponse(responseCode = "201")]
            )
        ),
        RouterOperation(
            path = JediHandlerV1.JEDIS_URI,
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "getJedis",
                responses = [ApiResponse(
                    responseCode = "200",
                    content = [Content(schema = Schema(implementation = JediResponse::class))]
                )]
            )
        ),
        RouterOperation(
            path = JediHandlerV1.JEDIS_ID_URI,
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "getJedi",
                parameters = [Parameter(name = "id", `in` = ParameterIn.PATH)],
                responses = [ApiResponse(
                    responseCode = "200",
                    content = [Content(schema = Schema(implementation = JediResponse::class))]
                )]
            )
        ),
        RouterOperation(
            path = JediHandlerV1.JEDIS_ID_URI,
            method = [RequestMethod.PUT],
            operation = Operation(
                operationId = "updateJedi",
                requestBody = RequestBody(
                    content = [Content(schema = Schema(implementation = JediRequest::class))]
                ),
                parameters = [Parameter(name = "id", `in` = ParameterIn.PATH)],
                responses = [ApiResponse(responseCode = "204")]
            )
        ),
        RouterOperation(
            path = JediHandlerV1.JEDIS_ID_URI,
            method = [RequestMethod.DELETE],
            operation = Operation(
                operationId = "deleteJedi",
                parameters = [Parameter(name = "id", `in` = ParameterIn.PATH)],
                responses = [ApiResponse(responseCode = "204")]
            )
        )
    )
    @Bean
    fun jediRoutes(handler: JediHandlerV1): RouterFunction<ServerResponse> =
        RouterFunctions.route()
            .POST(
                JediHandlerV1.JEDIS_URI, accept(MediaType.APPLICATION_JSON)
            ) { request -> handler.createJedis(request) }
            .GET(
                JediHandlerV1.JEDIS_URI, accept(MediaType.APPLICATION_JSON)
            ) { request -> handler.getJedis(request) }
            .GET(
                JediHandlerV1.JEDIS_ID_URI, accept(MediaType.APPLICATION_JSON)
            ) { request -> handler.getJedi(request) }
            .PUT(
                JediHandlerV1.JEDIS_ID_URI, accept(MediaType.APPLICATION_JSON)
            ) { request -> handler.updateJedi(request) }
            .DELETE(
                JediHandlerV1.JEDIS_ID_URI, accept(MediaType.APPLICATION_JSON)
            ) { request -> handler.deleteJedi(request) }
            .build()
}
