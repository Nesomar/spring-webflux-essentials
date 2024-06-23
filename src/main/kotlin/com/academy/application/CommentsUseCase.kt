package com.academy.application

import com.academy.application.ports.CommentService
import com.academy.application.ports.CommentWebClientService
import com.academy.domain.Comment
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class CommentsUseCase(
    private val commentWebClientService: CommentWebClientService,
    private val commentService: CommentService
) {

    fun getAllComments(page: Int, size: Int): Flux<Comment> {
        return commentService.findAllPaged(page, size)
            .switchIfEmpty(commentWebClientService.getAllComments(page, size).flatMap {
                commentService.save(it.copy(id = null))
            })
    }

    @Cacheable(value = ["comment"], key = "#postId")
    fun getCommentsByPostId(postId: Int): Flux<Comment> {
        return commentService.findByPostId(postId)
            .switchIfEmpty(commentWebClientService.getCommentsByPostId(postId).flatMap {
                commentService.save(it.copy(id = null))
            })
    }

    @CacheEvict(value = ["comment"], key = "#postId", allEntries = true)
    fun deleteAllComments(): Mono<Void> {
        return commentService.deleteAllComments()
    }
}