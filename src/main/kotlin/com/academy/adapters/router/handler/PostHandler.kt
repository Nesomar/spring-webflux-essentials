package com.academy.adapters.router.handler

import com.academy.adapters.router.mapper.PostResponseMapper.toResponse
import com.academy.adapters.router.response.PostResponse
import com.academy.application.PostUseCase
import com.academy.domain.Post
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class PostHandler(
    private val postUseCase: PostUseCase
) {

    fun getAllPosts(request: ServerRequest): Mono<ServerResponse> {
        val page = request.queryParam("page").orElse("1").toInt()
        val size = request.queryParam("size").orElse("5").toInt()
        return buildOkResponse(postUseCase.getAllPosts(page, size).map { it.toResponse() })
    }

    fun getPostById(request: ServerRequest): Mono<ServerResponse> {
        val postId = request.pathVariable("id").toInt()
        return buildOkResponse(postUseCase.getPostById(postId).map { it.toResponse() })
    }

    fun createPost(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Post::class.java)
            .flatMap { post -> buildOkResponse(postUseCase.createPost(post).map { it.toResponse() }) }
    }

    fun updatePost(request: ServerRequest): Mono<ServerResponse> {
        val postId = request.pathVariable("id").toInt()
        return request.bodyToMono(Post::class.java)
            .flatMap { post -> buildOkResponse(postUseCase.updatePost(post.copy(id = postId)).map { it.toResponse() }) }
    }

    fun deletePost(request: ServerRequest): Mono<ServerResponse> {
        val postId = request.pathVariable("id").toInt()
        return postUseCase.deletePost(postId)
            .then(ServerResponse.noContent().build())
    }

    private fun <T> buildOkResponse(body: Mono<T>): Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
    }

    private fun <T> buildOkResponse(body: Flux<T>): Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(body, PostResponse::class.java)
    }
}