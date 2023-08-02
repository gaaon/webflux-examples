package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private val log = kLogger()

@OptIn(ExperimentalStdlibApi::class)
private fun CoroutineScope.dispatcher(): CoroutineDispatcher? {
    return this.coroutineContext[CoroutineDispatcher.Key]
}

fun main() {
    runBlocking {
        log.info("thread: {}", Thread.currentThread().name)
        log.info("dispatcher: {}", this.dispatcher())

        withContext(Dispatchers.Default) {
            log.info("thread: {}", Thread.currentThread().name)
            log.info("dispatcher: {}", this.dispatcher())
        }

        withContext(Dispatchers.IO) {
            log.info("thread: {}", Thread.currentThread().name)
            log.info("dispatcher: {}", this.dispatcher())
        }

        CoroutineScope(CoroutineName("cs")).launch {
            log.info("thread: {}", Thread.currentThread().name)
            log.info("dispatcher: {}", this.dispatcher())
        }
    }
}