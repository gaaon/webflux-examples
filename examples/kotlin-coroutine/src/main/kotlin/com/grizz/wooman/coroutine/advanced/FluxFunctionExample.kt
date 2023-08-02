package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.flux

private suspend fun square(x: Int): Int {
    delay(10)
    return x * x
}

private val log = kLogger()

fun main() {
    flux<Int> {
        channel.send(square(10))
        channel.send(square(20))
        channel.send(square(30))
    }.subscribe {
        log.info("result: {}", it)
    }
}