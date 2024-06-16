package com.academy.application

import com.academy.application.ports.StudentService
import com.academy.domain.Student
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class StudentUseCase(
    private val studentService: StudentService
) {

    fun getAllStudents(): Flux<Student> {
        return studentService.getAllStudents()
    }

    @Cacheable(value = ["student"], key = "#id", unless = "#result == null")
    fun getStudentById(id: Long): Mono<Student> {
        return studentService.getStudentById(id)
    }

    fun createStudent(student: Student): Mono<Student> {
        return studentService.createStudent(student)
    }

    @CachePut(value = ["student"], key = "#id")
    fun updateStudent(id: Long, toDomain: Student): Mono<Student> {
        return studentService.updateStudent(id, toDomain)
    }

    @CacheEvict(value = ["student"], key = "#student.id", allEntries = true)
    fun deleteStudent(id: Long): Mono<Void> {
        return studentService.deleteStudent(id)
    }
}