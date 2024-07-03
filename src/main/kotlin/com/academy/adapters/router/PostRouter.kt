package com.academy.adapters.router

import com.academy.adapters.router.handler.PostHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class PostRouter(private val postHandler: PostHandler) {

    @Bean
    fun route(): RouterFunction<ServerResponse> = RouterFunctions
        .route()
        .GET("/$URI", postHandler::getAllPosts)
        .GET("/$URI/{postId}", postHandler::getPostById)
        .POST("/$URI", postHandler::createPost)
        .PUT("/$URI/{postId}", postHandler::updatePost)
        .DELETE("/$URI/{postId}", postHandler::deletePost)
        .build()

    companion object {
        private const val URI = "posts"
    }
}