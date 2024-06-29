package com.academy.adapters.controller.comments

import com.academy.adapters.controller.comments.mapper.CommentResponseMapper.toResponse
import com.academy.adapters.controller.comments.response.CommentResponse
import com.academy.application.CommentsUseCase
import com.academy.domain.Comment
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.random.Random

@WebFluxTest(CommentsController::class)
@ExtendWith(MockitoExtension::class)
class CommentsControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var commentsUseCase: CommentsUseCase

    @BeforeEach
    fun setUp() {
        Mockito.reset(commentsUseCase)
    }

    @Test
    fun `getAllComments should return a list of comments`() {
        val page = 0
        val size = 10
        val postId = null

        val comment = createComments()

        Mockito.`when`(commentsUseCase.getAllComments(postId, page, size))
            .thenReturn(Flux.just(comment))

        webTestClient.get()
            .uri("/comments?page=$page&size=$size")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CommentResponse::class.java)
            .hasSize(1)
            .contains(comment.toResponse())
    }

    @Test
    fun `getCommentsByPostId should return a list of comments for a given postId`() {
        val postId = 1
        val page = 0
        val size = 10

        val comment = createComments()

        Mockito.`when`(commentsUseCase.getAllComments(postId, page, size))
            .thenReturn(Flux.just(comment))

        webTestClient.get()
            .uri("/comments?postId=$postId&page=$page&size=$size")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CommentResponse::class.java)
            .hasSize(1)
            .contains(comment.toResponse())
    }

    @Test
    fun `deleteAllComments should delete all comments`() {
        Mockito.`when`(commentsUseCase.deleteAllComments())
            .thenReturn(Mono.empty())

        webTestClient.delete()
            .uri("/comments")
            .exchange()
            .expectStatus().isOk
    }

    private fun createCommentsResponse() : CommentResponse =
        CommentResponse(
            postId = Random.nextInt(1, 50),
            id = Random.nextInt(1, 50),
            name = "John Doe",
            email = "john.doe@example.com",
            body = "This is a test comment."
        )

    private fun createComments(): Comment =
        Comment(
            postId = Random.nextInt(1, 50),
            id = Random.nextInt(1, 50),
            name = "John Doe",
            email = "john.doe@example.com",
            body = "This is a test comment."
        )
}