package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val log = kLogger()

private suspend fun child() {
    log.info("context in suspend: {}", coroutineContext)

    val result = suspendCoroutine<Int> { cont ->
        log.info("context by continuation: {}",
            cont.context)
        cont.resume(100)
    }
    log.info("result: {}", result)
}

fun main() {
    runBlocking {
        log.info("context in CoroutineScope: {}",
            this.coroutineContext)
        child()
    }
}