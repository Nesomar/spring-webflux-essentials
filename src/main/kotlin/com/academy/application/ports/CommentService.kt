package com.academy.application.ports

import com.academy.domain.Comment
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CommentService {

    /**
     * Saves the given comment.
     *
     * @param comment the comment to be saved
     * @return a Mono emitting the saved comment
     */
    fun save(comment: Comment): Mono<Comment>

    /**
     * Retrieves a paginated list of comments.
     *
     * @param page the page number to retrieve.
     * @param size the number of comments per page.
     * @return a Flux containing the comments for the specified page.
     */
    fun findAllPaged(page: Int, size: Int): Flux<Comment>

    /**
     * Finds comments by the given post ID.
     *
     * @param postId the ID of the post
     * @return a Flux emitting the comments associated with the given post ID
     */
    fun findByPostId(postId: Int): Flux<Comment>

    /**
     * Deletes all comments.
     *
     * @return a Mono that completes when all comments have been deleted
     */
    fun deleteAllComments(): Mono<Void>

}