package com.github.manerajona.reactive.ports.output.gateway.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SwapiListResponseDto(
    @JsonProperty("message")
    val message: String? = null,

    @JsonProperty("total_records")
    val totalRecords: Int? = null,

    @JsonProperty("total_pages")
    val totalPages: Int? = null,

    @JsonProperty("previous")
    val previous: Any? = null,

    @JsonProperty("next")
    val next: String? = null,

    @JsonProperty("results")
    val results: List<ResultDto> = arrayListOf()
)
