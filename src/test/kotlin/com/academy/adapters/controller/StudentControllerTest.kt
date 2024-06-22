package com.academy.adapters.controller

import com.academy.adapters.controller.mapper.StudentRequestMapper.toDomain
import com.academy.adapters.controller.mapper.StudentResponseMapper.toResponse
import com.academy.adapters.controller.request.StudentRequest
import com.academy.adapters.controller.response.StudentResponse
import com.academy.application.StudentUseCase
import com.academy.domain.Student
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import kotlin.test.Test

class StudentControllerTest {

    private lateinit var studentUseCase: StudentUseCase
    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp() {
        studentUseCase = mockk()
        val studentController = StudentController(studentUseCase)
        webTestClient = WebTestClient.bindToController(studentController).build()
    }

    @Test
    fun `should get all students`() {
        val students = createStudent()
        every { studentUseCase.getAllStudents() } returns Flux.fromIterable(listOf(students))

        webTestClient.get()
            .uri("/students")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(StudentResponse::class.java)
            .hasSize(1)
            .contains(students.toResponse())

        verify { studentUseCase.getAllStudents() }
    }

    @Test
    fun `should get student by id`() {
        val student = createStudent()
        every { studentUseCase.getStudentById(1) } returns Mono.just(student)

        webTestClient.get()
            .uri("/students/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(StudentResponse::class.java)
            .isEqualTo(student.toResponse())

        verify { studentUseCase.getStudentById(1) }
    }

    @Test
    fun `should create student`() {
        val studentRequest = createStudentRequest()
        val studentResponse = createStudentResponse()
        every { studentUseCase.createStudent(studentRequest.toDomain()) } returns Mono.just(createStudent())

        webTestClient.post()
            .uri("/students")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(studentRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(StudentResponse::class.java)
            .isEqualTo(studentResponse)

        verify { studentUseCase.createStudent(studentRequest.toDomain()) }
    }

    @Test
    fun `should update student`() {
        val studentRequest = createStudentRequest()
        val studentResponse = createStudentResponse()
        every { studentUseCase.updateStudent(1, studentRequest.toDomain()) } returns Mono.just(createStudent())

        webTestClient.put()
            .uri("/students/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(studentRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody(StudentResponse::class.java)
            .isEqualTo(studentResponse)

        verify { studentUseCase.updateStudent(1, studentRequest.toDomain()) }
    }

    @Test
    fun `should delete student`() {
        every { studentUseCase.deleteStudent(1) } returns Mono.empty()

        webTestClient.delete()
            .uri("/students/1")
            .exchange()
            .expectStatus().isNoContent

        verify { studentUseCase.deleteStudent(1) }
    }

    private fun createStudent() = Student(
        id = 1,
        firstName = "John",
        lastName = "Doe",
        age = 20,
        email = "john.doe@example.com",
        enrollmentDate = LocalDate.parse("2021-09-01")
    )

    private fun createStudentRequest() = StudentRequest(
        firstName = "John",
        lastName = "Doe",
        age = 20,
        email = "john.doe@example.com",
        enrollmentDate = "2021-09-01"
    )

    private fun createStudentResponse() = StudentResponse(
        id = 1,
        firstName = "John",
        lastName = "Doe",
        age = 20,
        email = "john.doe@example.com",
        enrollmentDate = LocalDate.parse("2021-09-01")
    )
}