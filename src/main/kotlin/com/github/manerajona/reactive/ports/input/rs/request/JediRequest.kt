package com.github.manerajona.reactive.ports.input.rs.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class JediRequest(
    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s\\-]+", message = "The name field must contain only text without numbers")
    val name: String,
    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s\\-]+", message = "The name field must contain only text without numbers")
    val gender: String,
    @NotBlank
    val birthYear: String,
    @NotBlank
    val planet: String,
    @NotBlank
    val url: String
)
