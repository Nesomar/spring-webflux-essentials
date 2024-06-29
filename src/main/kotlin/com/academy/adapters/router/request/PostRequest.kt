package com.academy.adapters.router.request

data class PostRequest(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
