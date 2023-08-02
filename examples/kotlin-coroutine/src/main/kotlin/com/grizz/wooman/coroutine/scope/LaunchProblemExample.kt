package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

fun main() {
    runBlocking {
        val job = CoroutineScope(EmptyCoroutineContext).launch {
            val job1 = launch {
                delay(100)
                log.info("complete job1")
            }

            val job2 = launch {
                delay(100)
                log.info("complete job2")
            }

            val job3 = launch {
                delay(100)
                log.info("complete job3")
            }

            log.info("step1")
            job1.join()
            job2.join()
            job3.join()
            log.info("step2")
        }

        job.join()
    }
}