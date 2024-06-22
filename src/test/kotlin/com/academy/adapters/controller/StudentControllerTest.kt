package com.academy.adapters.controller

import com.academy.adapters.controller.mapper.StudentRequestMapper.toDomain
import com.academy.adapters.controller.mapper.StudentResponseMapper.toResponse
import com.academy.adapters.controller.request.StudentRequest
import com.academy.adapters.controller.response.StudentResponse
import com.academy.application.StudentUseCase
import com.academy.domain.Student
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebFluxTest(StudentController::class)
class StudentControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var studentUseCase: StudentUseCase

    @BeforeEach
    fun setUp() {
        studentUseCase = mock(StudentUseCase::class.java)
        val studentController = StudentController(studentUseCase)
        webTestClient = WebTestClient.bindToController(studentController).build()
    }

    @Test
    fun `getAllStudents should return all students`() {
        val students = listOf(createStudent())
        Mockito.`when`(studentUseCase.getAllStudents()).thenReturn(Flux.fromIterable(students))

        webTestClient.get().uri("/students")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(StudentResponse::class.java)
            .hasSize(1)
            .contains(students[0].toResponse())
    }

    @Test
    fun `getStudentById should return student by id`() {
        val student = createStudent()
        Mockito.`when`(studentUseCase.getStudentById(1)).thenReturn(Mono.just(student))

        webTestClient.get().uri("/students/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(StudentResponse::class.java)
            .isEqualTo(student.toResponse())
    }

    @Test
    fun `createStudent should create a new student`() {
        val studentRequest = createStudentRequest()
        val studentResponse = createStudentResponse()
        Mockito.`when`(studentUseCase.createStudent(studentRequest.toDomain())).thenReturn(Mono.just(createStudent()))

        webTestClient.post().uri("/students")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(studentRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(StudentResponse::class.java)
            .isEqualTo(studentResponse)
    }

    @Test
    fun `updateStudent should update an existing student`() {
        val studentRequest = createStudentRequest()
        val studentResponse = createStudentResponse()
        Mockito.`when`(studentUseCase.updateStudent(1, studentRequest.toDomain())).thenReturn(Mono.just(createStudent()))

        webTestClient.put().uri("/students/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(studentRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody(StudentResponse::class.java)
            .isEqualTo(studentResponse)
    }

    @Test
    fun `deleteStudent should delete a student`() {
        Mockito.`when`(studentUseCase.deleteStudent(1)).thenReturn(Mono.empty())

        webTestClient.delete().uri("/students/1")
            .exchange()
            .expectStatus().isNoContent
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