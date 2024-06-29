package com.academy.application

import com.academy.application.ports.CommentService
import com.academy.application.ports.CommentWebClientService
import com.academy.application.ports.StudentService
import com.academy.domain.Comment
import kotlin.random.Random
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class CommentsUseCaseTest {

    private lateinit var commentWebClientService: CommentWebClientService
    private lateinit var commentService: CommentService
    private lateinit var commentsUseCase: CommentsUseCase

    @BeforeEach
    fun setUp() {
        commentWebClientService = mock(CommentWebClientService::class.java)
        commentService = mock(CommentService::class.java)
        commentsUseCase = CommentsUseCase(commentWebClientService, commentService)
    }

    @Test
    fun `getAllComments should return comments from service if available`() {

    }

    @Test
    fun `getAllComments should fetch and save comments if not available in service`() {
    }

    @Test
    fun `getCommentsByPostId should return comments from service if available`() {
    }

    @Test
    fun `getCommentsByPostId should fetch and save comments if not available in service`() {
    }

    @Test
    fun `deleteAllComments should call deleteAllComments on service`() {
    }

    private fun createComments(): Comment =
        Comment(
            postId = Random.nextInt(1, 50),
            id = Random.nextInt(1, 50),
            name = "John Doe",
            email = "john.doe@example.com",
            body = "This is a test comment."
        )
}