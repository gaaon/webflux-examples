package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

fun main() {
    runBlocking {
        val job = CoroutineScope(EmptyCoroutineContext).launch {
            log.info("step1")
            val result = coroutineScope {
                val deferred1 = async {
                    delay(100)
                    100
                }

                val deferred2 = async {
                    delay(100)
                    200
                }

                val deferred3 = async {
                    delay(100)
                    300
                }

                deferred1.await() +
                        deferred2.await() +
                        deferred3.await()
            }
            log.info("result: {}", result)
            log.info("step2")
        }

        job.join()
    }
}