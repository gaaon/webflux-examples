package com.grizz.wooman.coroutine.intro

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()
fun main() {
    runBlocking {
        val context = CoroutineName("custom name") +
                CoroutineExceptionHandler { _, e ->
                    log.error("custom exception handler")
                }

        CoroutineScope(Dispatchers.IO).launch(context) {
            throw IllegalStateException()
        }

        delay(100)
    }
}