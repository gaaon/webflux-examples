package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()
fun main() {
    runBlocking {
        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                launch {
                    launch {
                        throw IllegalStateException("exception in launch")
                    }
                }
            } catch (e: Exception) {
                log.error("not caught maybe")
            }
        }

        job.join()
    }
}