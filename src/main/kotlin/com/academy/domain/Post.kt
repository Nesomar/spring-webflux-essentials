package com.academy.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Post @JsonCreator constructor(
    @JsonProperty("userId")  val userId: Int? = null,
    @JsonProperty("id")  val id: Int,
    @JsonProperty("title")  val title: String,
    @JsonProperty("body")  val body: String
)
