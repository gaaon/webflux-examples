package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking

private val log = kLogger()
fun main() {
    runBlocking {
        log.info("key: {}", CoroutineName.Key)
    }
}