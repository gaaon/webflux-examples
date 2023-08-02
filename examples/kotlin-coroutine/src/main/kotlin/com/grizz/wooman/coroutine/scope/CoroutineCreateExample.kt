package com.grizz.wooman.coroutine.scope

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

fun main() {
    val cs = CoroutineScope(EmptyCoroutineContext)

    log.info("context: {}", cs.coroutineContext)
    log.info("class name: {}", cs.javaClass.simpleName)

    cs.launch {
        log.info("context: {}", this.coroutineContext)
        log.info("class name: {}", this.javaClass.simpleName)
    }
}