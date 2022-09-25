package com.github.manerajona.reactive.ports.output.gateway.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SwapiSingleResponseDto(
    @JsonProperty("message")
    val message: String? = null,

    @JsonProperty("result")
    val result: ResultDto? = null
)
