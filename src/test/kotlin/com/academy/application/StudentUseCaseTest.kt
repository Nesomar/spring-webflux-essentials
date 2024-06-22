package com.academy.application

import com.academy.application.ports.StudentService
import com.academy.domain.Student
import com.academy.domain.exception.StudentException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class StudentUseCaseTest {

    private lateinit var studentService: StudentService
    private lateinit var studentUseCase: StudentUseCase

    @BeforeEach
    fun setUp() {
        studentService = mock(StudentService::class.java)
        studentUseCase = StudentUseCase(studentService)
    }

    @Test
    fun `getAllStudents should return all students`() {
        val students = listOf(Student(1, "John Doe"), Student(2, "Jane Doe"))
        `when`(studentService.getAllStudents()).thenReturn(Flux.fromIterable(students))

        val result = studentUseCase.getAllStudents()

        StepVerifier.create(result)
            .expectNext(students[0])
            .expectNext(students[1])
            .verifyComplete()
    }

    @Test
    fun `getStudentById should return student by id`() {
        val student = Student(1, "John Doe")
        `when`(studentService.getStudentById(1)).thenReturn(Mono.just(student))

        val result = studentUseCase.getStudentById(1)

        StepVerifier.create(result)
            .expectNext(student)
            .verifyComplete()
    }

    @Test
    fun `createStudent should create a new student`() {
        val student = Student(1, "John Doe")
        `when`(studentService.createStudent(student)).thenReturn(Mono.just(student))

        val result = studentUseCase.createStudent(student)

        StepVerifier.create(result)
            .expectNext(student)
            .verifyComplete()
    }

    @Test
    fun `updateStudent should update an existing student`() {
        val student = Student(1, "John Doe")
        `when`(studentService.updateStudent(1, student)).thenReturn(Mono.just(student))

        val result = studentUseCase.updateStudent(1, student)

        StepVerifier.create(result)
            .expectNext(student)
            .verifyComplete()
    }

    @Test
    fun `deleteStudent should delete a student`() {
        val student = Student(1, "John Doe")
        `when`(studentService.getStudentById(1)).thenReturn(Mono.just(student))
        `when`(studentService.deleteStudent(student)).thenReturn(Mono.empty())

        val result = studentUseCase.deleteStudent(1)

        StepVerifier.create(result)
            .verifyComplete()
    }

    @Test
    fun `getAllStudents should handle errors`() {
        `when`(studentService.getAllStudents()).thenReturn(Flux.error(RuntimeException("Error")))

        val result = studentUseCase.getAllStudents()

        StepVerifier.create(result)
            .expectErrorMatches { it is StudentException && it.message!!.contains("query all students") }
            .verify()
    }

    @Test
    fun `getStudentById should handle errors`() {
        `when`(studentService.getStudentById(1)).thenReturn(Mono.error(RuntimeException("Error")))

        val result = studentUseCase.getStudentById(1)

        StepVerifier.create(result)
            .expectErrorMatches { it is StudentException && it.message!!.contains("query the student by id") }
            .verify()
    }

    @Test
    fun `createStudent should handle errors`() {
        val student = Student(1, "John Doe")
        `when`(studentService.createStudent(student)).thenReturn(Mono.error(RuntimeException("Error")))

        val result = studentUseCase.createStudent(student)

        StepVerifier.create(result)
            .expectErrorMatches { it is StudentException && it.message!!.contains("save the student") }
            .verify()
    }

    @Test
    fun `updateStudent should handle errors`() {
        val student = Student(1, "John Doe")
        `when`(studentService.updateStudent(1, student)).thenReturn(Mono.error(RuntimeException("Error")))

        val result = studentUseCase.updateStudent(1, student)

        StepVerifier.create(result)
            .expectErrorMatches { it is StudentException && it.message!!.contains("update the student") }
            .verify()
    }

    @Test
    fun `deleteStudent should handle errors`() {
        `when`(studentService.getStudentById(1)).thenReturn(Mono.error(RuntimeException("Error")))

        val result = studentUseCase.deleteStudent(1)

        StepVerifier.create(result)
            .expectErrorMatches { it is StudentException && it.message!!.contains("delete the student") }
            .verify()
    }
}