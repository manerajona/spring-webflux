package com.github.manerajona.reactive.ports.output.r2dbc

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.*

interface JediR2dbcRepo : ReactiveCrudRepository<JediTable, UUID>
