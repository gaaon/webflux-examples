package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import com.grizz.wooman.coroutine.help.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val channel = Channel<Int>()

        launch(Dispatchers.IO) {
            log.info("sending")
            channel.send(1)
            channel.send(2)
            channel.send(3)
            channel.close()
        }

        for (value in channel) {
            log.info("value: {}", value)
        }
    }
}