package com.github.manerajona.reactive.ports.input.rs.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class JediResponse(
    val id: UUID,
    val name: String,
    val gender: String,
    @JsonProperty("birth_year")
    val birthYear: String,
    val planet: String,
    val url: String,
)
