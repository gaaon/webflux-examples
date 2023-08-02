package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private val log = kLogger()
private fun threadName(): String {
    return Thread.currentThread().name
}

fun main() {
    runBlocking {
        launch(Dispatchers.Unconfined) {
            log.info("thread1: {}", threadName())
            withContext(Dispatchers.IO) {
                log.info("thread in withContext: {}",
                    threadName())
            }
            log.info("thread2: {}", threadName())
            delay(100)
            log.info("thread3: {}", threadName())
        }
    }
}