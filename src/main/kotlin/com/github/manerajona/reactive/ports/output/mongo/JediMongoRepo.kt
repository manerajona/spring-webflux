package com.github.manerajona.reactive.ports.output.mongo

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.*

interface JediMongoRepo : ReactiveMongoRepository<JediDocument, UUID>
