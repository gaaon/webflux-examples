package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()

@OptIn(ExperimentalCoroutinesApi::class)
fun main() {
    runBlocking {
        val cs = CoroutineScope(Dispatchers.Default)
        log.info("job1: {}", cs.coroutineContext[Job])
        log.info("parent1: {}", cs.coroutineContext[Job]?.parent)

        cs.launch {
            log.info("job2: {}", this.coroutineContext[Job])
            log.info("parent2: {}", this.coroutineContext[Job]?.parent)

            launch {
                log.info("job3: {}", this.coroutineContext[Job])
                log.info("parent3: {}", this.coroutineContext[Job]?.parent)
            }

            launch {
                log.info("job4: {}", this.coroutineContext[Job])
                log.info("parent4: {}", this.coroutineContext[Job]?.parent)
            }
        }
    }
}