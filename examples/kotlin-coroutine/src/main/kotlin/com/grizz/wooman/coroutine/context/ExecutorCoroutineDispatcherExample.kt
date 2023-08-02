package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private fun threadName(): String {
    return Thread.currentThread().name
}

private val log = kLogger()

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    runBlocking {
        val single = newSingleThreadContext("single")
        val fixed = newFixedThreadPoolContext(4, "fixed")

        val job = launch(single) {
            log.info("thread1: {}", threadName())

            withContext(fixed) {
                log.info("thread2: {}", threadName())

                withContext(Dispatchers.IO) {
                    log.info("thread3: {}", threadName())

                    withContext(single) {
                        log.info("thread4: {}", threadName())
                    }
                }
            }
        }

        job.join()
        single.close()
        fixed.close()
    }
}