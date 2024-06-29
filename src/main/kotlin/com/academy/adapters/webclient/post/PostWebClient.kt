package com.academy.adapters.webclient.post

import com.academy.adapters.webclient.post.mapper.PostResponseMapper.toDomain
import com.academy.adapters.webclient.post.response.PostResponse
import com.academy.domain.Post
import com.academy.domain.exception.WebClientReactiveException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class PostWebClient(private val webClient: WebClient) {

    private val uri = "posts"

    fun getAllPosts(page: Int, size: Int): Flux<Post> {
        return makeGetRequest("/$uri?_page=$page&_limit=$size")
            .bodyToFlux(PostResponse::class.java)
            .map { it.toDomain() }
    }

    fun getPostById(postId: Int): Mono<Post> {
        return makeGetRequest("$uri/{id}", postId)
            .bodyToMono(PostResponse::class.java)
            .map { it.toDomain() }
    }

    fun createPost(post: Post): Mono<Post> {
        return makePostRequest("/$uri", post)
            .bodyToMono(PostResponse::class.java)
            .map { it.toDomain() }
    }

    fun updatePost(id: Int, post: Post): Mono<Post> {
        return makePutRequest("$uri/{id}", id, post)
            .bodyToMono(PostResponse::class.java)
            .map { it.toDomain() }
    }

    fun deletePost(id: Int): Mono<Void> {
        return makeDeleteRequest("$uri/{id}", id)
            .bodyToMono(Void::class.java)
    }

    private fun makeGetRequest(uri: String, vararg uriVariables: Any): WebClient.ResponseSpec {
        return webClient.get()
            .uri(uri, *uriVariables)
            .retrieve()
            .onStatus({ it.isError }, { response -> handleError(response) })
    }

    private fun makePostRequest(uri: String, body: Any): WebClient.ResponseSpec {
        return webClient.post()
            .uri(uri)
            .bodyValue(body)
            .retrieve()
            .onStatus({ it.isError }, { response -> handleError(response) })
    }

    private fun makePutRequest(uri: String, id: Int, body: Any): WebClient.ResponseSpec {
        return webClient.put()
            .uri(uri, id)
            .bodyValue(body)
            .retrieve()
            .onStatus({ it.isError }, { response -> handleError(response) })
    }

    private fun makeDeleteRequest(uri: String, id: Int): WebClient.ResponseSpec {
        return webClient.delete()
            .uri(uri, id)
            .retrieve()
            .onStatus({ it.isError }, { response -> handleError(response) })
    }

    private fun handleError(response: ClientResponse): Mono<Throwable> {
        return response.bodyToMono(String::class.java)
            .flatMap { errorBody -> Mono.error(WebClientReactiveException("An unexpected error occurred while " +
                    "accessing the external posts api detail: $errorBody")) }
    }
}