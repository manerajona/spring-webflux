package com.github.manerajona.reactive.core.model

import java.util.*

data class Jedi(
    val id: UUID? = null,
    val name: String,
    val gender: String,
    val birthYear: String,
    val planet: String,
    val url: String
)
