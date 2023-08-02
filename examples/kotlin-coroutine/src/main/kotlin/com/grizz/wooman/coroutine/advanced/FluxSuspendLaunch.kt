package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.runBlocking
import reactor.core.publisher.Flux

private suspend fun square(x: Int): Int {
    delay(10)
    return x * x
}

private val log = kLogger()
fun main() {
    Flux.create<Int> {
        runBlocking {
            it.next(square(10))
            it.next(square(20))
            it.next(square(30))
        }
    }.subscribe {
        log.info("result: {}", it)
    }
}