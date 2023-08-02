package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono
import reactor.util.context.Context

private val log = kLogger()

fun main() {
    val greeting = mono {
        launch(Dispatchers.IO) {
            val context = this.coroutineContext[ReactorContext]
                ?.context
            val who = context?.get<String>("who")
                ?: "world"
            log.info("hello, $who")

            val newContext = (context ?: Context.empty()).put("who", "taewoo")
            launch(ReactorContext(newContext)) {
                val context = this.coroutineContext[ReactorContext]
                    ?.context

                Mono.create<String> {
                    val who = it.contextView().getOrDefault("who", "world")
                    it.success("hello, $who")
                }.contextWrite((context ?: Context.empty()))
                    .subscribe {
                        log.info(it)
                    }
            }
        }
    }

    greeting
        .contextWrite { it.put("who", "grizz") }
        .subscribe()

    Thread.sleep(1000)
}