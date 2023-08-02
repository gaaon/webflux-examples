package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

fun main() {
    runBlocking {
        val job = CoroutineScope(EmptyCoroutineContext).launch {
            log.info("step1")
            coroutineScope {
                launch {
                    delay(100)
                    log.info("complete job1")
                }

                launch {
                    delay(100)
                    log.info("complete job2")
                }

                launch {
                    delay(100)
                    log.info("complete job3")
                }
            }
            log.info("step2")
        }

        job.join()
    }
}