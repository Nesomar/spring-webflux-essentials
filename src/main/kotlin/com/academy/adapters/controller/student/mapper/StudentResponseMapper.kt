package com.academy.adapters.controller.student.mapper

import com.academy.adapters.controller.student.response.StudentResponse
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