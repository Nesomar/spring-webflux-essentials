package com.academy.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Comment @JsonCreator constructor(
    @JsonProperty("postId") val postId: Int,
    @JsonProperty("id") val id: Int? = null,
    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("body") val body: String
)
