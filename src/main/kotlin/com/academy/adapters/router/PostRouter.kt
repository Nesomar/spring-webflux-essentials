package com.academy.adapters.router

import com.academy.adapters.router.handler.PostHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class PostRouter(private val postHandler: PostHandler) {

    private val uri = "posts"

    @Bean
    fun route(): RouterFunction<ServerResponse> = RouterFunctions
        .route()
        .GET("/$uri", postHandler::getAllPosts)
        .GET("/$uri/{id}", postHandler::getPostById)
        .POST("/$uri", postHandler::createPost)
        .PUT("/$uri/{id}", postHandler::updatePost)
        .DELETE("/$uri/{id}", postHandler::deletePost)
        .build()
}