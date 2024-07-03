package com.academy.adapters.service

import com.academy.adapters.webclient.post.PostWebClient
import com.academy.application.ports.PostWebClientService
import com.academy.domain.Post
import com.academy.domain.exception.WebClientReactiveException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PostWebClientServiceImpl(
    private val postWebClient: PostWebClient
) : PostWebClientService {

    override fun getAllPosts(page: Int, size: Int): Flux<Post> {
        return postWebClient.getAllPosts(page, size)
            .onErrorMap {
                WebClientReactiveException(
                    "An unexpected error occurred while accessing the external posts api detail: ${it.message}"
                )
            }
    }

    override fun getPostById(postId: Int): Mono<Post> {
        return postWebClient.getPostById(postId)
            .onErrorMap {
                WebClientReactiveException(
                    "An unexpected error occurred while accessing the external posts api detail: ${it.message}"
                )
            }
    }

    override fun createPost(post: Post): Mono<Post> {
        TODO("Not yet implemented")
    }

    override fun updatePost(post: Post): Mono<Post> {
        TODO("Not yet implemented")
    }

    override fun deletePost(postId: Int): Mono<Void> {
        TODO("Not yet implemented")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PostWebClientServiceImpl::class.java)
    }
}