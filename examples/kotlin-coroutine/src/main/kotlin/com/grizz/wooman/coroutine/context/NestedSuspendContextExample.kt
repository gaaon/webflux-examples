package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

private val log = kLogger()
private suspend fun nested() {
    log.info("context in nested: {}", coroutineContext)
}

suspend fun outer() {
    log.info("context in outer: {}", coroutineContext)
    nested()
}

fun main() {
    runBlocking {
        outer()
    }
}