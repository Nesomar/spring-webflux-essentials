package com.academy.adapters.webclient.post

import com.academy.adapters.webclient.post.mapper.PostResponseMapper.toDomain
import com.academy.adapters.webclient.post.response.PostResponse
import com.academy.domain.Post
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class PostWebClient(private val webClient: WebClient) {

    fun getAllPosts(page: Int, size: Int): Flux<Post> {
        return makeGetRequest("/$URI?_page=$page&_limit=$size")
            .bodyToFlux(PostResponse::class.java)
            .map { it.toDomain() }
    }

    fun getPostById(postId: Int): Mono<Post> {
        return makeGetRequest("$URI/{id}", postId)
            .bodyToMono(PostResponse::class.java)
            .map { it.toDomain() }
    }

    fun createPost(post: Post): Mono<Post> {
        return webClient.post()
            .uri(URI)
            .bodyValue(post)
            .retrieve()
            .bodyToMono(PostResponse::class.java)
            .map { it.toDomain() }
    }

    fun updatePost(id: Int, post: Post): Mono<Post> {
        return webClient.put()
            .uri("$URI/{id}", id)
            .bodyValue(post)
            .retrieve()
            .bodyToMono(PostResponse::class.java)
            .map { it.toDomain() }
    }

    fun deletePost(id: Int): Mono<Void> {
        return webClient.delete()
            .uri("$URI/{id}", id)
            .retrieve()
            .bodyToMono(Void::class.java)
    }

    private fun makeGetRequest(uri: String, vararg uriVariables: Any): WebClient.ResponseSpec {
        return webClient.get()
            .uri(uri, *uriVariables)
            .retrieve()
    }

    companion object {
        private const val URI = "posts"
    }
}