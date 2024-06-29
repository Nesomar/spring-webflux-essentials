package com.academy.application.ports

import com.academy.domain.Comment
import reactor.core.publisher.Flux

interface CommentWebClientService {

    /**
     * Retrieves comments for a specific post by its ID.
     *
     * @param postId The ID of the post for which to retrieve comments.
     * @param page The page number to retrieve.
     * @param size The number of comments per page.
     * @return A Flux stream of comments for the specified post.
     */
    fun getCommentsByPostId(postId: Int, page: Int, size: Int): Flux<Comment>

    /**
     * Retrieves all comments with pagination.
     *
     * @param page The page number to retrieve.
     * @param size The number of comments per page.
     * @return A Flux stream of all comments.
     */
    fun getAllComments(page: Int, size: Int): Flux<Comment>
}