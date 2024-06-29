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

    fun getAllComments(postId: Int?, page: Int, size: Int): Flux<Comment> {
        return if (postId == null) {
            getAllPagedComments(page, size)
        } else {
            getCommentsByPostId(postId, page, size)
        }
    }

    @CacheEvict(value = ["comment"], key = "#postId", allEntries = true)
    fun deleteAllComments(): Mono<Void> {
        return commentService.deleteAllComments()
    }

    private fun getAllPagedComments(page: Int, size: Int): Flux<Comment> {
        return commentService.findAllPaged(page, size)
            .switchIfEmpty(fetchAndSaveAllComments(page, size))
    }

    private fun fetchAndSaveAllComments(page: Int, size: Int): Flux<Comment> {
        return commentWebClientService.getAllComments(page, size)
            .flatMap { comment ->
                commentService.save(comment.copy(id = null))
            }
    }

    @Cacheable(value = ["comment"], key = "#postId", unless = "#result == null")
    private fun getCommentsByPostId(postId: Int, page: Int, size: Int): Flux<Comment> {
        return commentService.findByPostId(postId)
            .switchIfEmpty(fetchAndSaveCommentsByPostId(postId, page, size))
    }

    private fun fetchAndSaveCommentsByPostId(postId: Int, page: Int, size: Int): Flux<Comment> {
        return commentWebClientService.getCommentsByPostId(postId, page, size)
            .flatMap { comment ->
                commentService.save(comment.copy(id = null))
            }
    }
}