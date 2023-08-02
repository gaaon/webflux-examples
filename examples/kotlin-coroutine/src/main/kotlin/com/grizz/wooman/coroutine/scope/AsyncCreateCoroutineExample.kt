package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

@OptIn(ExperimentalCoroutinesApi::class)
fun main() {
    runBlocking {
        val cs = CoroutineScope(EmptyCoroutineContext)
        log.info("job: {}", cs.coroutineContext[Job])

        val deferred = cs.async {
            // coroutine created
            delay(100)
            log.info("context: {}", this.coroutineContext)
            log.info("class name: {}", this.javaClass.simpleName)
            log.info("parent: {}", this.coroutineContext[Job]?.parent)

            100
        }

        log.info("step1")
        log.info("result: {}", deferred.await())
        log.info("step2")
    }
}