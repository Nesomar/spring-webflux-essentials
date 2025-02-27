package com.academy.adapters.service

import com.academy.adapters.webclient.comments.CommentsWebClient
import com.academy.domain.Comment
import com.academy.domain.exception.CommentException
import com.academy.domain.exception.CommentsNotFoundException
import com.academy.domain.exception.WebClientReactiveException
import kotlin.random.Random
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class CommentWebClientServiceImplTest {

    @Mock
    private lateinit var commentsWebClient: CommentsWebClient

    @InjectMocks
    private lateinit var commentWebClientService: CommentWebClientServiceImpl

    private val sampleComments = listOf(
        createComments(),
        createComments()
    )

    @Test
    fun `getAllComments should return comments`() {
        `when`(commentsWebClient.getAllComments(1, 10)).thenReturn(Flux.fromIterable(sampleComments))

        val result = commentWebClientService.getAllComments(1, 10)

        StepVerifier.create(result)
            .expectNextSequence(sampleComments)
            .verifyComplete()

        verify(commentsWebClient, times(1)).getAllComments(1, 10)
    }

    @Test
    fun `getCommentsByPostId should return comments`() {
        `when`(commentsWebClient.getCommentsByPostId(1, 1, 5)).thenReturn(Flux.fromIterable(sampleComments))

        val result = commentWebClientService.getCommentsByPostId(1, 1, 5)

        StepVerifier.create(result)
            .expectNextSequence(sampleComments)
            .verifyComplete()

        verify(commentsWebClient, times(1)).getCommentsByPostId(1, 1, 5)
    }

    @Test
    fun `getCommentsByPostId should return error when no comments found`() {
        `when`(commentsWebClient.getCommentsByPostId(1, 1, 5)).thenReturn(Flux.empty())

        val result = commentWebClientService.getCommentsByPostId(1, 1, 5)

        StepVerifier.create(result)
            .expectErrorMatches { it is CommentsNotFoundException && it.message == "Comment not found with postId 1" }
            .verify()

        verify(commentsWebClient, times(1)).getCommentsByPostId(1, 1, 5)
    }

    @Test
    fun `fetchComments should handle errors`() {
        val errorMessage = "Some error"
        `when`(commentsWebClient.getAllComments(1, 10))
            .thenReturn(Flux.error(RuntimeException(errorMessage)))

        val result = commentWebClientService.getAllComments(1, 10)

        StepVerifier.create(result)
            .expectErrorMatches {
                it is WebClientReactiveException
                        && it.message == "An unexpected error occurred while trying to call the external comments API, detail: $errorMessage" }
            .verify()

        verify(commentsWebClient, times(1)).getAllComments(1, 10)
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