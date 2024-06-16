package com.academy.adapters.service

import com.academy.adapters.repository.StudentRepository
import com.academy.adapters.service.mapper.StudentEntityMapper.toDomain
import com.academy.adapters.service.mapper.StudentEntityMapper.toEntity
import com.academy.application.ports.StudentService
import com.academy.domain.Student
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class StudentServiceImpl(
    private val studentRepository: StudentRepository
) : StudentService {

    override fun getAllStudents(): Flux<Student> {
        return studentRepository.findAll().map { it.toDomain() }
    }

    override fun getStudentById(id: Long): Mono<Student> {
        return studentRepository.findById(id).map { it.toDomain() }
    }

    override fun createStudent(student: Student): Mono<Student> {
        return studentRepository.save(student.toEntity()).map { it.toDomain() }
    }

    override fun updateStudent(id: Long, student: Student): Mono<Student> {
        return studentRepository.findById(id)
            .flatMap { studentEntity ->
                val updateStudent = studentEntity.copy(
                    firstName = student.firstName,
                    lastName = student.lastName,
                    age = student.age,
                    email = student.email,
                    enrollmentDate = student.enrollmentDate
                )
                studentRepository.save(updateStudent).map { it.toDomain() }
            }
    }

    override fun deleteStudent(id: Long): Mono<Void> {
        return studentRepository.deleteById(id)
    }
}