package com.academy.adapters.repository

import com.academy.adapters.repository.entity.CommentEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface CommentRepository : ReactiveCrudRepository<CommentEntity, Int> {

    /**
     * Retrieves a paginated list of CommentEntity objects.
     *
     * @param page the page number to retrieve.
     * @param size the number of items per page.
     * @return a Flux stream of CommentEntity objects for the specified page.
     */
    @Query("SELECT * FROM comments OFFSET :page LIMIT :size")
    fun findAllPaged(page: Int, size: Int): Flux<CommentEntity>

    /**
     * Finds comments by the given post ID.
     *
     * @param postId The ID of the post for which to find comments.
     * @return A Flux stream of CommentEntity objects associated with the given post ID.
     */
    fun findByPostId(postId: Int): Flux<CommentEntity>
}