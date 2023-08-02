package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()
fun main() {
    val handler = CoroutineExceptionHandler { _, e ->
        log.error("exception handler")
    }

    runBlocking(handler) {
        throw IllegalStateException("exception in runBlocking")
    }
}