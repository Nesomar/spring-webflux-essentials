package com.academy.adapters.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("comments")
data class CommentEntity(
    @field:Id
    val id: Int? = null,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)
