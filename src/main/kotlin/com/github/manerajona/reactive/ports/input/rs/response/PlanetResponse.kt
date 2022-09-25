package com.github.manerajona.reactive.ports.input.rs.response

import com.fasterxml.jackson.annotation.JsonProperty

class PlanetResponse(
    val id: String,
    val name: String,
    val population: Long,
    val climate: String,
    val terrain: String,
    val gravity: String,
    val diameter: Int,
    @JsonProperty("surface_water")
    val surfaceWater: Int,
    @JsonProperty("orbital_period")
    val orbitalPeriod: Int,
    @JsonProperty("rotation_period")
    val rotationPeriod: Int,
    val url: String,
)
