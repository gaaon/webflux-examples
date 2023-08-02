package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()
fun main() {
    try {
        runBlocking {
            throw IllegalStateException("exception in runBlocking")
        }
    } catch (e: IllegalStateException) {
        log.error("exception caught")
    }
}