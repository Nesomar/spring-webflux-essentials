package com.academy.adapters.router.mapper

import com.academy.adapters.router.request.PostRequest
import com.academy.domain.Post

object PostRequestMapper {

    fun PostRequest.toDomain(): Post =
        Post(
            userId = this.userId,
            id = this.id,
            body = this.body,
            title = this.title
        )
}