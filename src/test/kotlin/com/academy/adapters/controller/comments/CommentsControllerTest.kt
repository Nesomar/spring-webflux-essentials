package com.academy.adapters.controller.comments

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

@WebFluxTest(CommentsController::class)
@ExtendWith(MockitoExtension::class)
class CommentsControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var commentsUseCase: CommentsUseCase

    private val comment = Comment(
        id = 1,
        postId = 1,
        name = "Comment Test",
        email = "colaborador.teste@email.com",
        body = "doloribus at sed quis culpa deserunt consectetur qui praesentium\\naccusamus fugiat dicta\\nvoluptatem rerum ut " +
                "voluptate autem\\nvoluptatem repellendus aspernatur dolorem in"
    )

    private val commentResponse = CommentResponse(
        id = 1,
        postId = 1,
        name = "Comment Test",
        email = "colaborador.teste@email.com",
        body = "doloribus at sed quis culpa deserunt consectetur qui praesentium\\naccusamus fugiat dicta\\nvoluptatem rerum ut " +
                "voluptate autem\\nvoluptatem repellendus aspernatur dolorem in"
    )

    @BeforeEach
    fun setUp() {
        Mockito.reset(commentsUseCase)
    }

    @Test
    fun `getAllComments should return a list of comments`() {
        val page = 0
        val size = 10

        Mockito.`when`(commentsUseCase.getAllComments(page, size))
            .thenReturn(Flux.just(comment))

        webTestClient.get()
            .uri("/comments?page=$page&size=$size")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CommentResponse::class.java)
            .hasSize(1)
            .contains(commentResponse)
    }

    @Test
    fun `getCommentsByPostId should return a list of comments for a given postId`() {
        val postId = 1

        Mockito.`when`(commentsUseCase.getCommentsByPostId(postId))
            .thenReturn(Flux.just(comment))

        webTestClient.get()
            .uri("/comments/post/$postId")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CommentResponse::class.java)
            .hasSize(1)
            .contains(commentResponse)
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
}