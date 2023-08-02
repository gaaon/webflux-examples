package com.grizz.wooman.coroutine.config

import com.grizz.wooman.coroutine.handler.ChatCoroutineWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import java.util.Map

@Configuration
class MappingConfig {
    @Bean
    fun simpleUrlHandlerMapping(
        chatWebSocketHandler: ChatCoroutineWebSocketHandler
    ): SimpleUrlHandlerMapping {
        val urlMapper = Map.of("/chat", chatWebSocketHandler)
        return SimpleUrlHandlerMapping().also {
            it.order = 1
            it.urlMap = urlMapper
        }
    }
}