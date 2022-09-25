package com.github.manerajona.reactive.ports.output.gateway.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PlanetDto(
    @JsonProperty("diameter")
    val diameter: String? = null,

    @JsonProperty("rotation_period")
    val rotationPeriod: String? = null,

    @JsonProperty("orbital_period")
    val orbitalPeriod: String? = null,

    @JsonProperty("gravity")
    val gravity: String? = null,

    @JsonProperty("population")
    val population: String? = null,

    @JsonProperty("climate")
    val climate: String? = null,

    @JsonProperty("terrain")
    val terrain: String? = null,

    @JsonProperty("surface_water")
    val surfaceWater: String? = null,

    @JsonProperty("created")
    val created: String? = null,

    @JsonProperty("edited")
    val edited: String? = null,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("url")
    val url: String? = null
)
