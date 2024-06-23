package com.academy.adapters.service

import com.academy.adapters.repository.CommentRepository
import com.academy.adapters.service.mapper.CommentsMapper.toDomain
import com.academy.adapters.service.mapper.CommentsMapper.toEntity
import com.academy.application.ports.CommentService
import com.academy.domain.Comment
import com.academy.domain.exception.DataBaseException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository
) : CommentService {

    override fun save(comment: Comment): Mono<Comment> {
        return commentRepository.save(comment.toEntity())
            .map { it.toDomain() }
            .onErrorMap { DataBaseException("Save comments error: ${it.message}") }
    }

    override fun findAllPaged(page: Int, size: Int): Flux<Comment> {
        return commentRepository.findAllPaged(page, size)
            .map { it.toDomain() }
            .onErrorMap { DataBaseException("FindAll comments error: ${it.message}") }
    }

    override fun findByPostId(postId: Int): Flux<Comment> {
        return commentRepository.findByPostId(postId)
            .map { it.toDomain() }
            .onErrorMap { DataBaseException("FindByPostId comments error: ${it.message}") }
    }

    override fun deleteAllComments(): Mono<Void> {
        return commentRepository.deleteAll()
            .onErrorMap { DataBaseException("DeleteAllComments comments error: ${it.message}") }
    }
}