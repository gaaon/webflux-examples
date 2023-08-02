package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val cs = CoroutineScope(Dispatchers.Default)

        // launch1
        val job = cs.launch {
            // launch2
            val job2 = launch {
                try {
                    delay(1000)
                    log.info("job2: I'm done")
                } catch (e: Exception) {
                    log.info("job2: I'm cancelled")
                    log.info("e2: {}", e.message)
                }
            }

            // launch3
            launch {
                try {
                    delay(1000)
                    log.info("job3: I'm done")
                } catch (e: Exception) {
                    log.info("job3: I'm cancelled")
                    log.info("e3: {}", e.message)
                }
            }

            delay(100)
            job2.cancel()
        }

        job.join()
        log.info("job is cancelled: {}", job.isCancelled)
    }
}