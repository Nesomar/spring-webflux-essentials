package com.academy.application.ports

import com.academy.domain.Comment
import reactor.core.publisher.Flux

interface CommentWebClientService {

    /**
     * Retrieves all comments with pagination.
     *
     * @param page the page number to retrieve.
     * @param size the number of comments per page.
     * @return a Flux stream of Comment objects.
     */
    fun getAllComments(page: Int, size: Int): Flux<Comment>

    /**
     * Retrieves comments by a specific post ID.
     *
     * @param postId the ID of the post for which to retrieve comments.
     * @return a Flux stream of Comment objects associated with the specified post ID.
     */
    fun getCommentsByPostId(postId: Int): Flux<Comment>
}