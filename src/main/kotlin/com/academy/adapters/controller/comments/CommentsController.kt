package com.academy.adapters.controller.comments

import com.academy.adapters.controller.comments.mapper.CommentResponseMapper.toResponse
import com.academy.adapters.controller.comments.response.CommentResponse
import com.academy.application.CommentsUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/comments")
@Validated
class CommentsController(
    private val commentsUseCase: CommentsUseCase
) {

    @GetMapping
    fun getAllComments(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): Flux<CommentResponse> {
        return commentsUseCase.getAllComments(page, size).map { it.toResponse() }
    }

    @GetMapping("/post/{postId}")
    fun getCommentsByPostId(@PathVariable postId: Int): Flux<CommentResponse> {
        return commentsUseCase.getCommentsByPostId(postId).map { it.toResponse() }
    }

    @DeleteMapping
    fun deleteAllComments() : Mono<Void> {
        return commentsUseCase.deleteAllComments()
    }
}