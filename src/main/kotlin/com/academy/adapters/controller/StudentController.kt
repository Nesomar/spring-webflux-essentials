package com.academy.adapters.controller

import com.academy.adapters.controller.mapper.StudentRequestMapper.toDomain
import com.academy.adapters.controller.mapper.StudentResponseMapper.toResponse
import com.academy.adapters.controller.request.StudentRequest
import com.academy.adapters.controller.response.StudentResponse
import com.academy.application.StudentUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/students")
@Validated
class StudentController(
    private val studentUseCase: StudentUseCase
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllStudents(): Flux<StudentResponse> = studentUseCase.getAllStudents().map { it.toResponse() }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStudentById(@PathVariable id: Long): Mono<StudentResponse> =
        studentUseCase.getStudentById(id).map { it.toResponse() }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createStudent(@RequestBody @Valid student: StudentRequest): Mono<StudentResponse> =
        studentUseCase.createStudent(student.toDomain()).map { it.toResponse() }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateStudent(@PathVariable id: Long, @RequestBody @Valid student: StudentRequest): Mono<StudentResponse> =
        studentUseCase.updateStudent(id, student.toDomain()).map { it.toResponse() }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStudent(@PathVariable id: Long): Mono<Void> = studentUseCase.deleteStudent(id)
}