package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()
fun main() {
    runBlocking {
        val handler = CoroutineExceptionHandler { _, e ->
            log.error("exception caught in handler")
        }

        val deferred = CoroutineScope(Dispatchers.IO + handler).async {
            throw IllegalStateException("exception in launch")
            10
        }

        try {
            deferred.await()
        } catch (e: Exception) {
            log.info("exception caught in catch")
        }
    }
}