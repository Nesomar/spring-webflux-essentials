package com.academy.adapters.controller.comments

import com.academy.adapters.controller.comments.mapper.CommentResponseMapper.toResponse
import com.academy.adapters.controller.comments.response.CommentResponse
import com.academy.application.CommentsUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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
        @RequestParam postId: Int? = null,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 5
    ): Flux<CommentResponse> {
        return commentsUseCase.getAllComments(postId, page, size).map { it.toResponse() }
    }

    @DeleteMapping
    fun deleteAllComments() : Mono<Void> {
        return commentsUseCase.deleteAllComments()
    }
}