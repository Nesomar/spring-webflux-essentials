package com.academy.adapters.webclient.comments.mapper

import com.academy.adapters.webclient.comments.request.CommentRequest
import com.academy.domain.Comment

object CommentRequestMapper {

    fun CommentRequest.toDomain() : Comment =
        Comment(
            id = this.id,
            body = this.body,
            name = this.name,
            email = this.email,
            postId = this.postId
        )
}