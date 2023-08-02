package com.grizz.wooman.coroutine.usage.service

import com.grizz.wooman.coroutine.help.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

class GreetFutureServiceImpl : GreetFutureService {
    private suspend fun greeting(): String {
        delay(100)
        return "hello"
    }

    override fun findGreet(): CompletableFuture<String> {
        return CoroutineScope(Dispatchers.Default).future {
            greeting()
        }
    }
}

fun main() {
    val log = logger<GreetFutureServiceImpl>()
    val greetFutureService = GreetFutureServiceImpl()

    greetFutureService.findGreet()
        .thenAccept { greet ->
            log.info("greet: $greet")
        }
    Thread.sleep(1000)
}