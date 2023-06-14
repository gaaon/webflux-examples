package com.grizz.wooman.coroutine.filter

import com.grizz.wooman.webflux.auth.IamAuthentication
import com.grizz.wooman.webflux.service.AuthService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context

@Component
class SecurityWebFilter(
    private val authService: AuthService
) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return mono {
            val resp = exchange.response
            val iam = exchange.request.headers
                .getFirst("X-I-AM")
            if (exchange.request.uri.path == "/api/users/signup") {
                return@mono chain.filter(exchange).awaitSingleOrNull()
            }
            if (iam == null) {
                resp.setStatusCode(HttpStatus.UNAUTHORIZED)
                return@mono resp.setComplete().awaitSingleOrNull()
            }

            val name = authService.getNameByToken(iam)
                .awaitSingleOrNull()
            if (name == null) {
                resp.setStatusCode(HttpStatus.UNAUTHORIZED)
                return@mono resp.setComplete().awaitSingleOrNull()
            }

            val authentication = IamAuthentication(name)

            chain.filter(exchange)
                .contextWrite { context: Context ->
                    val newContext = ReactiveSecurityContextHolder
                        .withAuthentication(authentication)
                    context.putAll(newContext)
                }.awaitSingleOrNull()
        }
    }
}
