package com.academy.adapters.controller.comments.mapper

import com.academy.adapters.controller.comments.response.CommentResponse
import com.academy.domain.Comment

object CommentResponseMapper {

    fun Comment.toResponse() : CommentResponse =
        CommentResponse(
            id = checkNotNull(this.id),
            body = this.body,
            name = this.name,
            email = this.email,
            postId = this.postId
        )
}