package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

fun main() {
    runBlocking {
        suspend fun getResult(): Int {
            delay(100)
            return 100
        }

        val job = CoroutineScope(EmptyCoroutineContext).launch {
            log.info("context in root: {}", coroutineContext)

            val result = coroutineScope {
                log.info("context in coroutineScope: {}", coroutineContext)
                getResult()
            }
            log.info("result: {}", result)

            launch {
                log.info("context in launch: {}", coroutineContext)
            }
        }

        job.join()
    }
}