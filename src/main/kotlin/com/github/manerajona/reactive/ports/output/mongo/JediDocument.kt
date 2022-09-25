package com.github.manerajona.reactive.ports.output.mongo

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class JediDocument(
    var id: UUID?,
    var name: String,
    var gender: String,
    var birthYear: String,
    var planet: String,
    var url: String
) {
    constructor() : this(null, "", "", "", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val jedi = other as JediDocument
        return id == jedi.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}
