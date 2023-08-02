package com.grizz.wooman.coroutine.basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.awaitSingle
import reactor.core.publisher.Mono

class SuspendExample {
    suspend fun greet(who: String): String {
        delay(100)
        return getResult(who).awaitSingle()
    }

    private fun getResult(who: String): Mono<String> {
        return Mono.just("Hello, $who!")
    }
}