package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()

@OptIn(ExperimentalCoroutinesApi::class)
fun main() {
    runBlocking {
        val cs1 = CoroutineScope(CoroutineName("CS1"))
        log.info("context1: {}", cs1.coroutineContext)
        log.info("cs1: {}", cs1.javaClass.simpleName)
        val p1 = cs1.coroutineContext[Job]?.parent
        log.info("cs1 job parent: {}", p1)

        val job = cs1.launch {
            log.info("context2: {}", this.coroutineContext)
            log.info("cs2: {}", this.javaClass.simpleName)
            val p2 = this.coroutineContext[Job]?.parent
            log.info("cs2 job parent: {}", p2)

            launch {
                log.info("context3: {}", this.coroutineContext)
                val p3 = this.coroutineContext[Job]?.parent
                log.info("cs3 job parent: {}", p3)
            }
        }

        job.join()
    }
}