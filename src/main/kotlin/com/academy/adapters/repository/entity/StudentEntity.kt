package com.academy.adapters.repository.entity

import java.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("students")
data class StudentEntity(
    @field:Id
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val email: String,
    val enrollmentDate: LocalDate
)
