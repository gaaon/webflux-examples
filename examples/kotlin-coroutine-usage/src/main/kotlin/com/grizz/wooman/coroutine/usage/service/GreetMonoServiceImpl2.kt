package com.grizz.wooman.coroutine.usage.service

import com.grizz.wooman.coroutine.help.logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono
import kotlin.coroutines.coroutineContext

class GreetMonoServiceImpl2 : GreetMonoService {
    private suspend fun greeting(): String {
        delay(100)
        val who = coroutineContext[ReactorContext]
            ?.context
            ?.get<String>("who")
            ?: "world"

        return "hello, $who"
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
        .contextWrite {
            it.put("who", "taewoo")
        }
        .subscribe { greet ->
            log.info("greet: {}", greet)
        }
    Thread.sleep(1000)
}