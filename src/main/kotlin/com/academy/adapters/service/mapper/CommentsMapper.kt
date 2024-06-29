package com.academy.adapters.service.mapper

import com.academy.adapters.repository.entity.CommentEntity
import com.academy.domain.Comment

object CommentsMapper {

    fun Comment.toEntity(): CommentEntity =
        CommentEntity(
            id = this.id,
            postId = this.postId,
            email = this.email,
            body = this.body,
            name = this.name
        )

    fun CommentEntity.toDomain(): Comment =
        Comment(
            id = this.id,
            postId = this.postId,
            email = this.email,
            body = this.body,
            name = this.name
        )
}