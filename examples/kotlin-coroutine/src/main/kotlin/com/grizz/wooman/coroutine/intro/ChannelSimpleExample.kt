package com.grizz.wooman.coroutine.intro

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()
suspend fun main() = runBlocking {
    log.info("Start runBlocking")
    val channel = Channel<Int>()
    launch {
        delay(100)

        for (i in 0 until 5) {
            channel.send(i)
        }
        channel.close()
    }

    delay(500)

    for (i in channel) {
        log.info("item: {}", i)
    }
    log.info("Finish runBlocking")
}