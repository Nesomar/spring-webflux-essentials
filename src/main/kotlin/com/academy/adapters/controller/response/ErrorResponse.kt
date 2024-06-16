package com.academy.adapters.controller.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val errorCode: String,
    val errorMessage: String,
    val errorDetails: String? = null
)
