package com.academy.application

import com.academy.application.ports.PostWebClientService
import com.academy.domain.Post
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class PostUseCase(
    private val postWebClientService: PostWebClientService
) {

    fun getAllPosts(page: Int, size: Int): Flux<Post> {
        return postWebClientService.getAllPosts(page, size)
    }

    fun getPostById(postId: Int): Mono<Post> {
        return postWebClientService.getPostById(postId)
    }

    fun createPost(post: Post?): Mono<Post> {
        return postWebClientService.createPost(post!!)
    }

    fun updatePost(post: Post?): Mono<Post> {
        return postWebClientService.updatePost(post!!)
    }

    fun deletePost(postId: Int): Mono<Void> {
        return postWebClientService.deletePost(postId)
    }


}