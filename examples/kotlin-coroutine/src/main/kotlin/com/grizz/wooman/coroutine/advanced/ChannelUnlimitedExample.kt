package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import com.grizz.wooman.coroutine.help.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val channel = Channel<Int>(Channel.Factory.UNLIMITED)

        launch(Dispatchers.IO) {
            channel.send(1)
            log.info("sent 1")

            channel.send(2)
            log.info("sent 2")

            channel.send(3)
            log.info("sent 3")
        }

        for (i in (0 until 3)) {
            delay(100)
            log.info("value: {}", channel.receive())
        }
    }
}