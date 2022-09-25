package com.github.manerajona.reactive.config.exception

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import javax.validation.constraints.NotNull

@JsonPropertyOrder("code", "detail", "field", "value", "location")
data class ErrorDetails(
    var code: @NotNull ErrorCode,
    var detail: @NotNull String,
    var field: String?,
    var value: Any?,
    var location: ErrorLocation?
) {
    constructor(code: ErrorCode, detail: String) : this(code, detail, null, null, null)

    companion object Enums {

        enum class ErrorLocation {
            BODY, PATH, QUERY, HEADER
        }

        enum class ErrorCode(message: String) {
            INVALID_FIELD_VALUE("The provided field is not valid."),
            RESOURCE_NOT_FOUND("The requested resource was not found."),
            HTTP_CLIENT_ERROR("The request failed because a 4xx error was received."),
            SERVICE_UNAVAILABLE("The server is unavailable to handle this request right now. Please try again later."),
            INTERNAL_ERROR("There was an error on the server and the request could not be completed."),
            BAD_REQUEST("The server cannot return a response due to an error on the client’s end.");

            val defaultMessage: String = message
        }
    }
}
