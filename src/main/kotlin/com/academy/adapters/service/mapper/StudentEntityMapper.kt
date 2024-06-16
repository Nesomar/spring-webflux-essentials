package com.academy.adapters.service.mapper

import com.academy.adapters.repository.entity.StudentEntity
import com.academy.domain.Student

object StudentEntityMapper {

    fun StudentEntity.toDomain(): Student =
        Student(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            age = this.age,
            email = this.email,
            enrollmentDate = this.enrollmentDate
        )

    fun Student.toEntity(): StudentEntity =
        StudentEntity(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            age = this.age,
            email = this.email,
            enrollmentDate = this.enrollmentDate
        )
}