package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

private val log = kLogger()
private suspend fun nested() {
    log.info("context in nested: {}", coroutineContext)
}

fun main() {
    runBlocking {
        launch {
            log.info("context in launch: {}", this.coroutineContext)
            nested()
        }
    }
}