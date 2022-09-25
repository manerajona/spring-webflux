package com.github.manerajona.reactive.ports.input.rs.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class JediRequest(
    @field:NotBlank
    @field:Pattern(regexp = "[a-zA-Z\\s\\-]+", message = "The name field must contain only text without numbers")
    @field:Schema(name = "name", example = "Luke Skywalker", required = true)
    val name: String,
    @field:NotBlank
    @field:Pattern(regexp = "[a-zA-Z\\s\\-]+", message = "The name field must contain only text without numbers")
    @field:Schema(name = "gender", example = "Male", required = true)
    val gender: String,
    @field:NotBlank
    @field:Schema(name = "birth_year", example = "19BBY", required = true)
    val birthYear: String,
    @field:NotBlank
    @field:Schema(name = "planet", example = "Tatooine", required = true)
    val planet: String,
    @field:NotBlank
    @field:Schema(name = "url", example = "https://swapi.dev/api/people/1/", required = true)
    val url: String
)
