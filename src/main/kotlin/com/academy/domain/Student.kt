package com.academy.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.time.LocalDate

data class Student @JsonCreator constructor(
    @JsonProperty("id") val id: Long? = null,
    @JsonProperty("firstName") val firstName: String = "",
    @JsonProperty("lastName") val lastName: String= "",
    @JsonProperty("age") val age: Int = 0,
    @JsonProperty("email") val email: String = "",
    @JsonProperty("enrollmentDate") val enrollmentDate: LocalDate = LocalDate.now()
) : Serializable
