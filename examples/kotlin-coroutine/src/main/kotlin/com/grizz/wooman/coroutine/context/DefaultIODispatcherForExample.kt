package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private val log = kLogger()

fun main() {
    runBlocking {
        for (i in 1 until 1000) {
            launch(Dispatchers.Default) {
                log.info("thread: {}", Thread.currentThread().name)
            }

            launch(Dispatchers.IO) {
                log.info("thread: {}", Thread.currentThread().name)
            }
        }
    }
}