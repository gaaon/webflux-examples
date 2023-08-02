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
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()
fun main() {
    runBlocking(CoroutineName("runBlocking")) {
        log.info("context in runBlocking: {}", this.coroutineContext)

        launch {
            log.info("context in launch: {}", this.coroutineContext)
        }
    }
}