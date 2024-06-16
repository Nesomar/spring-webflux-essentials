package com.academy.adapters.repository

import com.academy.adapters.repository.entity.StudentEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface StudentRepository: ReactiveCrudRepository<StudentEntity, Long>