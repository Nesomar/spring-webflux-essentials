package com.academy.adapters.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class StudentRequest(
    @field:NotBlank(message = "firstName is mandatory.")
    val firstName: String,
    @field:NotBlank(message = "lastName is mandatory.")
    val lastName: String,
    @field:NotNull(message = "age is mandatory.")
    @field:Min(value = 16, message = "Minimum age for enrollment is 16 years.")
    val age: Int,
    @field:NotBlank(message = "Email is mandatory")
    @field:Email(message = "Email should be valid")
    val email: String,
    @field:NotBlank(message = "firstName is mandatory.")
    @field:Pattern(
        regexp = "\\d{4}-\\d{2}-\\d{2}",
        message = "Date must be in the format yyyy-MM-dd"
    )
    val enrollmentDate: String
)
