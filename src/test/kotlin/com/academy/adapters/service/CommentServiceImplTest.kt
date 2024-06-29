package com.academy.adapters.service

import com.academy.adapters.repository.CommentRepository
import com.academy.adapters.service.mapper.CommentsMapper.toEntity
import com.academy.domain.Comment
import com.academy.domain.exception.DataBaseException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import kotlin.random.Random

@ExtendWith(MockitoExtension::class)
class CommentServiceImplTest {

    @Mock
    private lateinit var commentRepository: CommentRepository

    @InjectMocks
    private lateinit var commentServiceImpl: CommentServiceImpl

    @Test
    fun `save should save comment and return saved comment`() {
        val comment = createComments()
        val commentEntity = comment.toEntity()

        `when`(commentRepository.save(any())).thenReturn(Mono.just(commentEntity))

        val result = commentServiceImpl.save(comment)

        StepVerifier.create(result)
            .expectNextMatches { it == comment }
            .verifyComplete()
    }

    @Test
    fun `save should handle error`() {
        val comment = createComments()
        val errorMessage = "Database error"

        `when`(commentRepository.save(any())).thenReturn(Mono.error(RuntimeException(errorMessage)))

        val result = commentServiceImpl.save(comment)

        StepVerifier.create(result)
            .expectErrorMatches { it is DataBaseException && it.message == "Save comments error: $errorMessage" }
            .verify()
    }

    @Test
    fun `findAllPaged should return paged comments`() {
        val comment = createComments()
        val commentEntity = comment.toEntity()

        `when`(commentRepository.findAllPaged(0, 10)).thenReturn(Flux.just(commentEntity))

        val result = commentServiceImpl.findAllPaged(0, 10)

        StepVerifier.create(result)
            .expectNextMatches { it == comment }
            .verifyComplete()
    }

    @Test
    fun `findAllPaged should handle error`() {
        val errorMessage = "Database error"

        `when`(commentRepository.findAllPaged(0, 10)).thenReturn(Flux.error(RuntimeException(errorMessage)))

        val result = commentServiceImpl.findAllPaged(0, 10)

        StepVerifier.create(result)
            .expectErrorMatches { it is DataBaseException && it.message == "FindAll comments error: $errorMessage" }
            .verify()
    }

    @Test
    fun `findByPostId should return comments for post`() {
        val comment = createComments()
        val commentEntity = comment.toEntity()

        `when`(commentRepository.findByPostId(1)).thenReturn(Flux.just(commentEntity))

        val result = commentServiceImpl.findByPostId(1)

        StepVerifier.create(result)
            .expectNextMatches { it == comment }
            .verifyComplete()
    }

    @Test
    fun `findByPostId should handle error`() {
        val errorMessage = "Database error"

        `when`(commentRepository.findByPostId(1)).thenReturn(Flux.error(RuntimeException(errorMessage)))

        val result = commentServiceImpl.findByPostId(1)

        StepVerifier.create(result)
            .expectErrorMatches { it is DataBaseException && it.message == "FindByPostId comments error: $errorMessage" }
            .verify()
    }

    @Test
    fun `deleteAllComments should delete all comments`() {
        `when`(commentRepository.deleteAll()).thenReturn(Mono.empty())

        val result = commentServiceImpl.deleteAllComments()

        StepVerifier.create(result)
            .verifyComplete()
    }

    @Test
    fun `deleteAllComments should handle error`() {
        val errorMessage = "Database error"

        `when`(commentRepository.deleteAll()).thenReturn(Mono.error(RuntimeException(errorMessage)))

        val result = commentServiceImpl.deleteAllComments()

        StepVerifier.create(result)
            .expectErrorMatches { it is DataBaseException && it.message == "DeleteAllComments comments error: $errorMessage" }
            .verify()
    }

    private fun createComments() : Comment =
        Comment(
            postId = Random.nextInt(1, 50),
            id = Random.nextInt(1, 50),
            name = "John Doe",
            email = "john.doe@example.com",
            body = "This is a test comment."
        )
}