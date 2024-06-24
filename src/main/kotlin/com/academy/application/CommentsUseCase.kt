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
        return fetchCommentsFromService(page, size)
            .switchIfEmpty(fetchAndSaveCommentsFromWebClient(page, size))
    }

    @Cacheable(value = ["comment"], key = "#postId")
    fun getCommentsByPostId(postId: Int): Flux<Comment> {
        return fetchCommentsByPostIdFromService(postId)
            .switchIfEmpty(fetchAndSaveCommentsByPostIdFromWebClient(postId))
    }

    @CacheEvict(value = ["comment"], allEntries = true)
    fun deleteAllComments(): Mono<Void> {
        return commentService.deleteAllComments()
    }

    private fun fetchCommentsFromService(page: Int, size: Int): Flux<Comment> {
        return commentService.findAllPaged(page, size)
    }

    private fun fetchAndSaveCommentsFromWebClient(page: Int, size: Int): Flux<Comment> {
        return commentWebClientService.getAllComments(page, size)
            .flatMap { comment ->
                commentService.save(comment.copy(id = null))
            }
    }

    private fun fetchCommentsByPostIdFromService(postId: Int): Flux<Comment> {
        return commentService.findByPostId(postId)
    }

    private fun fetchAndSaveCommentsByPostIdFromWebClient(postId: Int): Flux<Comment> {
        return commentWebClientService.getCommentsByPostId(postId)
            .flatMap { comment ->
                commentService.save(comment.copy(id = null))
            }
    }
}