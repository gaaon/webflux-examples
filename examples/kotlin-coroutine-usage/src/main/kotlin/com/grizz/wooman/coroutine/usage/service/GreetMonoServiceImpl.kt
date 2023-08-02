package com.grizz.wooman.coroutine.usage.service

import com.grizz.wooman.coroutine.help.logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono

class GreetMonoServiceImpl : GreetMonoService {
    private suspend fun greeting(): String {
        delay(100)
        return "hello"
    }

    override fun findGreet(): Mono<String> {
        return mono {
            greeting()
        }
    }
}

fun main() {
    val log = logger<GreetMonoServiceImpl2>()
    val greetMonoService = GreetMonoServiceImpl2()
    greetMonoService.findGreet()
        .subscribe { greet ->
            log.info("greet: {}", greet)
        }
    Thread.sleep(1000)
}