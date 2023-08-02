package com.grizz.wooman.coroutine.intro

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()
fun main() {
    val threadLocal = ThreadLocal<String>()
    threadLocal.set("hello")
    log.info("thread: {}", Thread.currentThread().name)
    log.info("threadLocal: {}", threadLocal.get())

    runBlocking {
        val context = CoroutineName("custom name") +
                Dispatchers.IO +
                threadLocal.asContextElement()

        launch(context) {
            log.info("thread: {}", Thread.currentThread().name)
            log.info("threadLocal: {}", threadLocal.get())
            log.info("coroutine name: {}",
                coroutineContext[CoroutineName])
        }
    }
}