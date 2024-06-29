package com.academy.adapters.router.mapper

import com.academy.adapters.router.response.PostResponse
import com.academy.domain.Post

object PostResponseMapper {

    fun Post.toResponse(): PostResponse =
        PostResponse(
            userId = checkNotNull(this.userId),
            id = this.id,
            body = this.body,
            title = this.title
        )
}