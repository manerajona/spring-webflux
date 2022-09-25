package com.github.manerajona.reactive.ports.output.gateway.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ResultDto(
    @JsonProperty("properties")
    val properties: PlanetDto? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("_id")
    val id: String? = null,

    @JsonProperty("uid")
    val uid: String? = null,

    @JsonProperty("__v")
    val v: Int? = null,

    @JsonProperty("url")
    val url: String? = null
)
