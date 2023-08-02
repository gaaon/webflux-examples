package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private val log = kLogger()

fun main() {
    runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            log.info("context in launch: {}", coroutineContext)

            withContext(Dispatchers.IO) {
                log.info("context in withContext: {}", coroutineContext)
            }

            log.info("context in launch again: {}", coroutineContext)
        }

        job.join()
    }
}