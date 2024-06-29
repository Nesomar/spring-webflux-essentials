package com.academy.adapters.service

import com.academy.adapters.repository.StudentRepository
import com.academy.adapters.service.mapper.StudentEntityMapper.toEntity
import com.academy.domain.Student
import com.academy.domain.exception.DataBaseException
import com.academy.domain.exception.StudentNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate

class StudentServiceImplTest {

    private lateinit var studentRepository: StudentRepository
    private lateinit var studentService: StudentServiceImpl

    @BeforeEach
    fun setUp() {
        studentRepository = mock(StudentRepository::class.java)
        studentService = StudentServiceImpl(studentRepository)
    }

    @Test
    fun `getAllStudents should return all students`() {
        val student = Student(1L, "John", "Doe", 20, "john.doe@example.com", LocalDate.parse("2023-01-01"))
        `when`(studentRepository.findAll()).thenReturn(Flux.just(student.toEntity()))

        StepVerifier.create(studentService.getAllStudents())
            .expectNext(student)
            .verifyComplete()
    }

    @Test
    fun `getAllStudents should handle errors`() {
        `when`(studentRepository.findAll()).thenReturn(Flux.error(RuntimeException("DB error")))

        StepVerifier.create(studentService.getAllStudents())
            .expectErrorMatches { it is DataBaseException && it.message == "getAllStudents error: DB error" }
            .verify()
    }

    @Test
    fun `getStudentById should return student by id`() {
        val student = Student(1L, "John", "Doe", 20, "john.doe@example.com", LocalDate.parse("2023-01-01"))
        `when`(studentRepository.findById(1L)).thenReturn(Mono.just(student.toEntity()))

        StepVerifier.create(studentService.getStudentById(1L))
            .expectNext(student)
            .verifyComplete()
    }

    @Test
    fun `getStudentById should handle student not found`() {
        `when`(studentRepository.findById(1L)).thenReturn(Mono.empty())

        StepVerifier.create(studentService.getStudentById(1L))
            .expectErrorMatches { it is StudentNotFoundException && it.message == "Student not found with id 1" }
            .verify()
    }

    @Test
    fun `createStudent should create a new student`() {
        val student = Student(1L, "John", "Doe", 20, "john.doe@example.com", LocalDate.parse("2023-01-01"))
        `when`(studentRepository.save(student.toEntity())).thenReturn(Mono.just(student.toEntity()))

        StepVerifier.create(studentService.createStudent(student))
            .expectNext(student)
            .verifyComplete()
    }

    @Test
    fun `createStudent should handle errors`() {
        val student = Student(1L, "John", "Doe", 20, "john.doe@example.com", LocalDate.parse("2023-01-01"))
        `when`(studentRepository.save(student.toEntity())).thenReturn(Mono.error(RuntimeException("DB error")))

        StepVerifier.create(studentService.createStudent(student))
            .expectErrorMatches { it is DataBaseException && it.message == "createStudent error: DB error" }
            .verify()
    }

    @Test
    fun `updateStudent should update an existing student`() {
        val student = Student(1L, "John", "Doe", 20, "john.doe@example.com", LocalDate.parse("2023-01-01"))
        val updatedStudent = student.copy(firstName = "Jane")
        `when`(studentRepository.findById(1L)).thenReturn(Mono.just(student.toEntity()))
        `when`(studentRepository.save(any())).thenReturn(Mono.just(updatedStudent.toEntity()))

        StepVerifier.create(studentService.updateStudent(1L, updatedStudent))
            .expectNext(updatedStudent)
            .verifyComplete()
    }

    @Test
    fun `updateStudent should handle student not found`() {
        val student = Student(1L, "John", "Doe", 20, "john.doe@example.com", LocalDate.parse("2023-01-01"))
        `when`(studentRepository.findById(1L)).thenReturn(Mono.empty())

        StepVerifier.create(studentService.updateStudent(1L, student))
            .expectErrorMatches { it is StudentNotFoundException && it.message == "Student not found with id 1" }
            .verify()
    }

    @Test
    fun `deleteStudent should delete a student`() {
        val student = Student(1L, "John", "Doe", 20, "john.doe@example.com", LocalDate.parse("2023-01-01"))
        `when`(studentRepository.delete(student.toEntity())).thenReturn(Mono.empty())

        StepVerifier.create(studentService.deleteStudent(student))
            .verifyComplete()
    }

    @Test
    fun `deleteStudent should handle errors`() {
        val student = Student(1L, "John", "Doe", 20, "john.doe@example.com", LocalDate.parse("2023-01-01"))
        `when`(studentRepository.delete(student.toEntity())).thenReturn(Mono.error(RuntimeException("DB error")))

        StepVerifier.create(studentService.deleteStudent(student))
            .expectErrorMatches { it is DataBaseException && it.message == "deleteStudent error: DB error" }
            .verify()
    }
}