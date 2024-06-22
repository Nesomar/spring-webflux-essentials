package com.academy.application

import com.academy.application.ports.StudentService
import com.academy.domain.Student
import com.academy.domain.exception.StudentException
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap

@Component
class StudentUseCase(
    private val studentService: StudentService
) {

    fun getAllStudents(): Flux<Student> {
        return studentService.getAllStudents()
            .handleErrors("query all students")
    }

    @Cacheable(value = ["student"], key = "#id", unless = "#result == null")
    fun getStudentById(id: Long): Mono<Student> {
        return studentService.getStudentById(id)
            .handleErrors("query the student by id")
    }

    fun createStudent(student: Student): Mono<Student> {
        return studentService.createStudent(student)
            .handleErrors("save the student")
    }

    @CachePut(value = ["student"], key = "#id")
    fun updateStudent(id: Long, student: Student): Mono<Student> {
        return studentService.updateStudent(id, student)
            .handleErrors("update the student")
    }

    @CacheEvict(value = ["student"], key = "#id", allEntries = true)
    fun deleteStudent(id: Long): Mono<Void> {
        return studentService.getStudentById(id)
            .handleErrors("delete the student")
            .flatMap { studentService.deleteStudent(it) }
    }

    private fun <T> Mono<T>.handleErrors(action: String): Mono<T> {
        return this.onErrorMap(Exception::class) {
            throw StudentException(
                "An unexpected error occurred while trying to $action, detail: ${it.message.orEmpty()}"
            )
        }
    }

    private fun <T> Flux<T>.handleErrors(action: String): Flux<T> {
        return this.onErrorMap(Exception::class) {
            throw StudentException(
                "An unexpected error occurred while trying to $action, detail: ${it.message.orEmpty()}"
            )
        }
    }
}