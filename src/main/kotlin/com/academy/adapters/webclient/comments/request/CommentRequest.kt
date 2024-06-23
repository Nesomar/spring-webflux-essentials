package com.academy.adapters.webclient.comments.request

data class CommentRequest(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
