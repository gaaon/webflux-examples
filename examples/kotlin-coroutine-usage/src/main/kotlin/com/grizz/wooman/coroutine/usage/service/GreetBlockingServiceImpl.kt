package com.grizz.wooman.coroutine.usage.service

import com.grizz.wooman.coroutine.help.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GreetBlockingServiceImpl : GreetBlockingService {
    private val log = logger<GreetBlockingServiceImpl>()

    private suspend fun greeting(): String {
        delay(100)
        return "hello"
    }

    override fun findGreet() {
        CoroutineScope(Dispatchers.IO).launch {
            log.info(greeting())
        }
    }
}

fun main() {
    val greetBlockingService = GreetBlockingServiceImpl()
    greetBlockingService.findGreet()
    Thread.sleep(1000)
}