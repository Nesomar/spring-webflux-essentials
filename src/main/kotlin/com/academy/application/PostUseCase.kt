package com.academy.application

import com.academy.application.ports.PostWebClientService
import com.academy.domain.Post
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
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

    @Cacheable(value = ["post"], key = "#postId", unless = "#result == null")
    fun getPostById(postId: Int): Mono<Post> {
        return postWebClientService.getPostById(postId)
    }

    fun createPost(post: Post): Mono<Post> {
        return postWebClientService.createPost(post)
    }

    @CachePut(value = ["post"], key = "#id")
    fun updatePost(post: Post?): Mono<Post> {
        return postWebClientService.updatePost(post!!)
    }

    @CacheEvict(value = ["post"], key = "#postId", allEntries = true)
    fun deletePost(postId: Int): Mono<Void> {
        return postWebClientService.deletePost(postId)
    }


}