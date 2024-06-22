package com.academy.adapters.service

import com.academy.adapters.repository.StudentRepository
import com.academy.adapters.service.mapper.StudentEntityMapper.toEntity
import com.academy.domain.Student
import com.academy.domain.exception.StudentNotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate

class StudentServiceImplTest {

    private lateinit var studentRepository: StudentRepository
    private lateinit var studentService: StudentServiceImpl

    @BeforeEach
    fun setUp() {
        studentRepository = mockk()
        studentService = StudentServiceImpl(studentRepository)
    }

    @Test
    fun `getAllStudents should return all students`() {
        val student = createStudent()

        every { studentRepository.findAll() } returns Flux.just(student.toEntity())

        val result = studentService.getAllStudents()

        StepVerifier.create(result)
            .expectNextMatches { it.firstName == "John" && it.lastName == "Doe" }
            .verifyComplete()

        verify { studentRepository.findAll() }
    }

    @Test
    fun `getStudentById should return student when found`() {
        val student = createStudent()

        every { studentRepository.findById(1) } returns Mono.just(student.toEntity())

        val result = studentService.getStudentById(1)

        StepVerifier.create(result)
            .expectNextMatches { it.firstName == "John" && it.lastName == "Doe" }
            .verifyComplete()

        verify { studentRepository.findById(1) }
    }

    @Test
    fun `getStudentById should return error when not found`() {

        every { studentRepository.findById(1) } returns Mono.empty()

        val result = studentService.getStudentById(1)

        StepVerifier.create(result)
            .expectError(StudentNotFoundException::class.java)
            .verify()

        verify { studentRepository.findById(1) }
    }

    @Test
    fun `createStudent should save and return student`() {
        val student = createStudent()
        every { studentRepository.save(any()) } returns Mono.just(student.toEntity())

        val result = studentService.createStudent(student)

        StepVerifier.create(result)
            .expectNextMatches { it.firstName == "John" && it.lastName == "Doe" }
            .verifyComplete()

        verify { studentRepository.save(any()) }
    }

    @Test
    fun `updateStudent should update and return student`() {
        val student = createStudent()
        val updatedStudent = student.copy(firstName = "Jane")
        every { studentRepository.findById(1) } returns Mono.just(student.toEntity())
        every { studentRepository.save(any()) } returns Mono.just(updatedStudent.toEntity())

        val result = studentService.updateStudent(1, updatedStudent)

        StepVerifier.create(result)
            .expectNextMatches { it.firstName == "Jane" && it.lastName == "Doe" }
            .verifyComplete()

        verify { studentRepository.findById(1) }
        verify { studentRepository.save(any()) }
    }

    @Test
    fun `deleteStudent should delete student`() {
        val student = createStudent()
        every { studentRepository.delete(any()) } returns Mono.empty()

        val result = studentService.deleteStudent(student)

        StepVerifier.create(result)
            .verifyComplete()

        verify { studentRepository.delete(any()) }
    }

    private fun createStudent() = Student(
        id = 1,
        firstName = "John",
        lastName = "Doe",
        age = 20,
        email = "john.doe@example.com",
        enrollmentDate = LocalDate.parse("2021-09-01")
    )
}