package com.academy.adapters.controller.handler

import com.academy.adapters.controller.response.ErrorResponse
import com.academy.domain.exception.DataBaseException
import com.academy.domain.exception.StudentException
import com.academy.domain.exception.StudentNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, exchange: ServerWebExchange): Mono<ResponseEntity<ErrorResponse>> {
        return buildErrorResponse(
            errorCode = "500",
            errorMessage = "An unexpected error occurred",
            errorDetails = mapOf("Message" to ex.message.orEmpty()),
            status = HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleValidationException(ex: WebExchangeBindException, exchange: ServerWebExchange): Mono<ResponseEntity<ErrorResponse>> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage.orEmpty() }
        return buildErrorResponse(
            errorCode = "400",
            errorMessage = "Validation failure",
            errorDetails = errors,
            status = HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(StudentNotFoundException::class)
    fun handleStudentNotFoundException(ex: StudentNotFoundException): Mono<ResponseEntity<ErrorResponse>> {
        return buildErrorResponse(
            errorCode = "404",
            errorMessage = "Student not fould.",
            errorDetails = mapOf("Message" to ex.message.orEmpty()),
            status = HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(DataBaseException::class)
    fun handleDataBaseException(ex: DataBaseException, exchange: ServerWebExchange): Mono<ResponseEntity<ErrorResponse>> {
        return buildErrorResponse(
            errorCode = "500",
            errorMessage = "Unexpected error accessing the database.",
            errorDetails = mapOf("Message" to ex.message.orEmpty()),
            status = HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(StudentException::class)
    fun handleGlobalException(ex: StudentException, exchange: ServerWebExchange): Mono<ResponseEntity<ErrorResponse>> {
        return buildErrorResponse(
            errorCode = "500",
            errorMessage = "An error occurred in the student's operation.",
            errorDetails = mapOf("Message" to ex.message.orEmpty()),
            status = HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    private fun buildErrorResponse(
        errorCode: String,
        errorMessage: String,
        errorDetails: Map<String, String>,
        status: HttpStatus
    ): Mono<ResponseEntity<ErrorResponse>> {
        val errorResponse = ErrorResponse(
            errorCode = errorCode,
            errorMessage = errorMessage,
            errorDetails = errorDetails
        )
        return Mono.just(ResponseEntity(errorResponse, status))
    }

}