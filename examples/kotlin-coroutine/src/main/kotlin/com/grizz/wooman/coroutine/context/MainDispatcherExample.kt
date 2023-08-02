package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private val log = kLogger()

@OptIn(ExperimentalStdlibApi::class)
private fun CoroutineScope.dispatcher(): CoroutineDispatcher? {
    return this.coroutineContext[CoroutineDispatcher.Key]
}

fun main() {
    runBlocking {
        withContext(Dispatchers.Main) {
            log.info("thread: {}", Thread.currentThread().name)
            log.info("dispatcher: {}", this.dispatcher())
        }
    }
}