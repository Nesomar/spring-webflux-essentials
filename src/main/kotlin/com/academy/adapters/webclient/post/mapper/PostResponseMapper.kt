package com.academy.adapters.webclient.post.mapper

import com.academy.adapters.webclient.post.response.PostResponse
import com.academy.domain.Post

object PostResponseMapper {

    fun PostResponse.toDomain(): Post =
        Post(
            userId = this.userId,
            id = this.id,
            title = this.title,
            body = this.body
        )
}