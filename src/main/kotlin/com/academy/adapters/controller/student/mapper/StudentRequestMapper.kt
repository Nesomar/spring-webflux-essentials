package com.academy.adapters.controller.student.mapper

import com.academy.adapters.controller.student.request.StudentRequest
import com.academy.domain.Student
import java.time.LocalDate

object StudentRequestMapper {

    fun StudentRequest.toDomain() : Student =
        Student(
            firstName = this.firstName,
            lastName = this.lastName,
            age = this.age,
            email = this.email,
            enrollmentDate = LocalDate.parse(this.enrollmentDate)
        )
}