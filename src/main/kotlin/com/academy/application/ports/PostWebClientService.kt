package com.academy.application.ports

import com.academy.domain.Post
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PostWebClientService {

    /**
     * Retrieves a paginated list of all posts.
     *
     * @param page the page number to retrieve.
     * @param size the number of posts per page.
     * @return a Flux stream of Post objects.
     */
    fun getAllPosts(page: Int, size: Int): Flux<Post>

    /**
     * Retrieves a post by its ID.
     * @param postId the ID of the post to retrieve.
     * @return a Mono containing the post if found, empty otherwise.
     */
    fun getPostById(postId: Int): Mono<Post>

    /**
     * Creates a new post.
     * @param post the post to create.
     * @return a Mono containing the created post.
     */
    fun createPost(post: Post): Mono<Post>

    /**
     * Updates an existing post.
     * @param post the post to update.
     * @return a Mono containing the updated post.
     */
    fun updatePost(post: Post): Mono<Post>

    /**
     * Deletes a post by its ID.
     * @param postId the ID of the post to delete.
     * @return a Mono indicating completion.
     */
    fun deletePost(postId: Int): Mono<Void>
}