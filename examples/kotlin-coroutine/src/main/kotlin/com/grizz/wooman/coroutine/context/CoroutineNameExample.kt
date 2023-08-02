package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private val log = kLogger()
fun main() {
    runBlocking(CoroutineName("runBlocking")) {
        log.info("name in runBlocking: {}",
            this.coroutineContext[CoroutineName])

        withContext(CoroutineName("withContext")) {
            log.info("name in withContext: {}",
                this.coroutineContext[CoroutineName])
        }
    }
}