package com.grizz.wooman.coroutine.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.WebSocketService
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono
import java.util.function.Predicate

@Configuration
class WebSocketConfig {
    @Bean
    fun webSocketHandlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter(webSocketService())
    }

    @Bean
    fun webSocketService(): WebSocketService {
        val webSocketService: HandshakeWebSocketService = object : HandshakeWebSocketService() {
            override fun handleRequest(exchange: ServerWebExchange, handler: WebSocketHandler): Mono<Void> {
                val iam = exchange.request
                    .headers
                    .getFirst("X-I-AM")

                if (iam == null) {
                    exchange.response.setStatusCode(HttpStatus.UNAUTHORIZED)
                    return exchange.response.setComplete()
                }

                return exchange.session
                    .flatMap { session: WebSession ->
                        session.attributes["iam"] = iam
                        super.handleRequest(exchange, handler)
                    }
            }
        }
        webSocketService.sessionAttributePredicate = Predicate { s: String? -> true }
        return webSocketService
    }
}