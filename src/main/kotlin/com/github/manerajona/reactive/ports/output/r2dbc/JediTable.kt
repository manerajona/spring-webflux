package com.github.manerajona.reactive.ports.output.r2dbc

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("jedi")
class JediTable(
    @Id var id: UUID?,
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
        val jedi = other as JediTable
        return id == jedi.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}
