package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val cs = CoroutineScope(Dispatchers.Default)
        val csJob = cs.coroutineContext[Job]

        // launch1
        val job = cs.launch {
            // launch2
            launch(SupervisorJob()) {
                delay(100)
                throw IllegalStateException("unexpected")
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
        }

        job.join()
        log.info("job is cancelled: {}", job.isCancelled)
        log.info("csJob is cancelled: {}", csJob?.isCancelled)
    }
}