package com.academy.adapters.controller.response

import java.time.LocalDate

data class StudentResponse(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val email: String,
    val enrollmentDate: LocalDate
)
