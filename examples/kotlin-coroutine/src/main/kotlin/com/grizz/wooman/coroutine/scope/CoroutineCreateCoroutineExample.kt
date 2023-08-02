package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

@OptIn(ExperimentalCoroutinesApi::class)
fun main() {
    runBlocking {
        val cs = CoroutineScope(EmptyCoroutineContext)

        val job = cs.launch {
            // coroutine1 created
            delay(100)
            log.info("job: {}", this.coroutineContext[Job])

            val job2 = this.launch {
                // coroutine2 created

                delay(500)
                log.info("parent job: {}",
                    this.coroutineContext[Job]?.parent)
                log.info("coroutine2 finished")
            }

            job2.join()
            log.info("coroutine1 finished")
        }

        log.info("step1")
        job.join()
        log.info("step2")
    }
}