package com.academy.adapters.webclient.comments

import com.academy.adapters.webclient.comments.mapper.CommentRequestMapper.toDomain
import com.academy.adapters.webclient.comments.request.CommentRequest
import com.academy.domain.Comment
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class CommentsWebClient(
    private val webClient: WebClient
) {

    fun getAllComments(page: Int, size: Int): Flux<Comment> {
        return webClient.get()
            .uri("/comments?_page=$page&_limit=$size")
            .retrieve()
            .bodyToFlux(CommentRequest::class.java)
            .map { it.toDomain() }
    }

    fun getCommentsByPostId(postId: Int): Flux<Comment> {
        return webClient.get()
            .uri("/comments?postId={postId}", postId)
            .retrieve()
            .bodyToFlux(CommentRequest::class.java)
            .map { it.toDomain() }
    }
}