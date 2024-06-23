package com.academy.adapters.service

import com.academy.adapters.webclient.comments.CommentsWebClient
import com.academy.application.ports.CommentWebClientService
import com.academy.domain.Comment
import com.academy.domain.exception.CommentException
import com.academy.domain.exception.WebClientReactiveException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CommentWebClientServiceImpl(
    private val commentsWebClient: CommentsWebClient
) : CommentWebClientService {

    override fun getAllComments(page: Int, size: Int): Flux<Comment> {
        return fetchComments { commentsWebClient.getAllComments(page, size) }
    }

    override fun getCommentsByPostId(postId: Int): Flux<Comment> {
        return fetchComments { commentsWebClient.getCommentsByPostId(postId) }
            .switchIfEmpty(Mono.error(CommentException("Comment not found with postId $postId")))
    }

    private fun fetchComments(fetchFunction: () -> Flux<Comment>): Flux<Comment> {
        return fetchFunction()
            .onErrorMap {
                WebClientReactiveException(
                    "An unexpected error occurred while trying to call the external comments API, detail: ${it.message}"
                )
            }
    }
}