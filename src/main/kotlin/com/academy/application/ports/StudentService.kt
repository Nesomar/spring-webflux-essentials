package com.academy.application.ports

import com.academy.domain.Student
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface StudentService {

    /**
     * Retrieves all students.
     * @return a Flux containing all students.
     */
    fun getAllStudents(): Flux<Student>

    /**
     * Retrieves a student by their ID.
     * @param id the ID of the student to retrieve.
     * @return a Mono containing the student, or empty if not found.
     */
    fun getStudentById(id: Long): Mono<Student>

    /**
     * Creates a new student.
     * @param student the student to create.
     * @return a Mono containing the created student.
     */
    fun createStudent(student: Student): Mono<Student>

    /**
     * Updates an existing student.
     * @param id the ID of the student to update.
     * @param student the student data to update.
     * @return a Mono containing the updated student, or empty if not found.
     */
    fun updateStudent(id: Long, student: Student): Mono<Student>

    /**
     * Deletes the specified student.
     *
     * @param student The student to be deleted.
     * @return A Mono signaling when the deletion has been completed.
     */
    fun deleteStudent(student: Student): Mono<Void>
}