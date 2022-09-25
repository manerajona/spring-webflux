package com.github.manerajona.reactive.config.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ErrorDetailsException(status: HttpStatus, val errors: List<ErrorDetails>) : ResponseStatusException(status)
