package com.academy.adapters.webclient.comments.mapper

import com.academy.adapters.webclient.comments.response.CommentResponse
import com.academy.domain.Comment

object CommentResponseMapper {

    fun CommentResponse.toDomain() : Comment =
        Comment(
            id = this.id,
            body = this.body,
            name = this.name,
            email = this.email,
            postId = this.postId
        )
}