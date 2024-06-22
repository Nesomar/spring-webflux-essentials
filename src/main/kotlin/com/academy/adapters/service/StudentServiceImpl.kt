package com.academy.adapters.service

import com.academy.adapters.repository.StudentRepository
import com.academy.adapters.service.mapper.StudentEntityMapper.toDomain
import com.academy.adapters.service.mapper.StudentEntityMapper.toEntity
import com.academy.application.ports.StudentService
import com.academy.domain.Student
import com.academy.domain.exception.DataBaseException
import com.academy.domain.exception.StudentNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class StudentServiceImpl(
    private val studentRepository: StudentRepository
) : StudentService {

    override fun getAllStudents(): Flux<Student> {
        return studentRepository.findAll()
            .map { it.toDomain() }
            .onErrorMap { DataBaseException("getAllStudents error: ${it.message}") }
    }

    override fun getStudentById(id: Long): Mono<Student> {
        return studentRepository.findById(id)
            .map { it.toDomain() }
            .onErrorMap { DataBaseException("getStudentById error: ${it.message}") }
            .switchIfEmpty(Mono.error(StudentNotFoundException("Student not found with id $id")))

    }

    override fun createStudent(student: Student): Mono<Student> {
        return studentRepository.save(student.toEntity())
            .map { it.toDomain() }
            .onErrorMap { DataBaseException("createStudent error: ${it.message}") }
    }

    override fun updateStudent(id: Long, student: Student): Mono<Student> {
        return studentRepository.findById(id)
            .onErrorMap { DataBaseException("getStudentById error: ${it.message}") }
            .switchIfEmpty(Mono.error(StudentNotFoundException("Student not found with id $id")))
            .flatMap { studentEntity ->
                val updatedStudent = studentEntity.copy(
                    firstName = student.firstName,
                    lastName = student.lastName,
                    age = student.age,
                    email = student.email,
                    enrollmentDate = student.enrollmentDate
                )
                studentRepository.save(updatedStudent)
                    .map { it.toDomain() }
                    .onErrorMap { DataBaseException("updateStudent error: ${it.message}") }
            }
    }

    override fun deleteStudent(student: Student): Mono<Void> {
        return studentRepository.delete(student.toEntity())
            .onErrorMap { DataBaseException("deleteStudent error: ${it.message}") }
    }
}