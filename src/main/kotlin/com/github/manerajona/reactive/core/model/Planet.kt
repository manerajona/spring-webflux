package com.github.manerajona.reactive.core.model

data class Planet(
    val id: String? = null,
    val name: String,
    val population: Long,
    val climate: String,
    val terrain: String,
    val gravity: String,
    val diameter: Int,
    val surfaceWater: Int,
    val orbitalPeriod: Int,
    val rotationPeriod: Int,
    val url: String
)
