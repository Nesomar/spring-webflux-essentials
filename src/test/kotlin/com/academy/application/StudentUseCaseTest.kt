package com.academy.application

import com.academy.application.ports.StudentService
import com.academy.domain.Student
import com.academy.domain.exception.StudentException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
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
        val student1 = Student(1, "John Doe")
        val student2 = Student(2, "Jane Doe")
        val students = listOf(student1, student2)
        `when`(studentService.getAllStudents()).thenReturn(Flux.fromIterable(students))

        StepVerifier.create(studentUseCase.getAllStudents())
            .expectNext(student1)
            .expectNext(student2)
            .verifyComplete();

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    fun `getStudentById should return student by id`() {
        val student = Student(1, "John Doe")
        `when`(studentService.getStudentById(1)).thenReturn(Mono.just(student))

        StepVerifier.create(studentUseCase.getStudentById(1L))
            .expectNext(student)
            .verifyComplete();

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    fun `createStudent should create a new student`() {
        val student = Student(1, "John Doe")
        `when`(studentService.createStudent(student)).thenReturn(Mono.just(student))

        StepVerifier.create(studentUseCase.createStudent(student))
            .expectNext(student)
            .verifyComplete();

        verify(studentService, times(1)).createStudent(student);
    }

    @Test
    fun `updateStudent should update an existing student`() {
        val student = Student(1, "John Doe")
        `when`(studentService.updateStudent(1, student)).thenReturn(Mono.just(student))

        StepVerifier.create(studentUseCase.updateStudent(1L, student))
            .expectNext(student)
            .verifyComplete();

        verify(studentService, times(1)).updateStudent(1L, student);
    }

    @Test
    fun `deleteStudent should delete a student`() {
        val student = Student(1, "John Doe")
        `when`(studentService.getStudentById(1)).thenReturn(Mono.just(student))
        `when`(studentService.deleteStudent(student)).thenReturn(Mono.empty())

        StepVerifier.create(studentUseCase.deleteStudent(1L))
            .verifyComplete();

        verify(studentService, times(1)).getStudentById(1L);
        verify(studentService, times(1)).deleteStudent(student);
    }
}