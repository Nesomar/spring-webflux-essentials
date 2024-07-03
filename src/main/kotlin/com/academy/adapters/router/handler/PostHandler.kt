package com.academy.adapters.router.handler

import com.academy.adapters.router.mapper.PostResponseMapper.toResponse
import com.academy.adapters.router.response.PostResponse
import com.academy.application.PostUseCase
import com.academy.domain.Post
import org.springframework.http.HttpStatus
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
        return handleRequest(postUseCase.getAllPosts(page, size).map { it.toResponse() })
    }

    fun getPostById(request: ServerRequest): Mono<ServerResponse> {
        val postId = request.pathVariable("postId").toInt()
        return handleRequest(postUseCase.getPostById(postId).map { it.toResponse() })
    }

    fun createPost(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Post::class.java)
            .flatMap { post -> handleRequest(postUseCase.createPost(post).map { it.toResponse() }) }
    }

    fun updatePost(request: ServerRequest): Mono<ServerResponse> {
        val postId = request.pathVariable("postId").toInt()
        return request.bodyToMono(Post::class.java)
            .flatMap { post -> handleRequest(postUseCase.updatePost(post.copy(id = postId)).map { it.toResponse() }) }
    }

    fun deletePost(request: ServerRequest): Mono<ServerResponse> {
        val postId = request.pathVariable("postId").toInt()
        return postUseCase.deletePost(postId)
            .then(ServerResponse.noContent().build())
            .onErrorResume { handleError(it) }
    }

    private fun <T> handleRequest(body: Mono<T>): Mono<ServerResponse> {
        return buildOkResponse(body)
            .onErrorResume { handleError(it) }
    }

    private fun <T> handleRequest(body: Flux<T>): Mono<ServerResponse> {
        return buildOkResponse(body)
            .switchIfEmpty(ServerResponse.notFound().build())
            .onErrorResume { handleError(it) }
    }

    private fun <T> buildOkResponse(body: Mono<T>): Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(body, PostResponse::class.java)
    }

    private fun <T> buildOkResponse(body: Flux<T>): Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(body, PostResponse::class.java)
    }

    private fun handleError(throwable: Throwable): Mono<ServerResponse> {
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .bodyValue("An error occurred: ${throwable.message}")
    }

}