package com.academy.adapters.controller.mapper

import com.academy.adapters.controller.response.StudentResponse
import com.academy.domain.Student

object StudentResponseMapper {

    fun Student.toResponse(): StudentResponse =
        StudentResponse(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            age = this.age,
            email = this.email,
            enrollmentDate = this.enrollmentDate
        )
}