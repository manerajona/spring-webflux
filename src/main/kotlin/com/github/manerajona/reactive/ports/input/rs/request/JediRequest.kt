package com.github.manerajona.reactive.ports.input.rs.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class JediRequest(
    @field:NotBlank
    @field:Pattern(regexp = "[a-zA-Z\\s\\-]+", message = "The name field must contain only text without numbers")
    val name: String,
    @field:NotBlank
    @field:Pattern(regexp = "[a-zA-Z\\s\\-]+", message = "The name field must contain only text without numbers")
    val gender: String,
    @field:NotBlank
    val birthYear: String,
    @field:NotBlank
    val planet: String,
    @field:NotBlank
    val url: String
)
