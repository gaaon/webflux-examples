package com.grizz.wooman.coroutine.intro

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

private val log = kLogger()
private fun range(n: Int): Flow<Int> {
    return flow {
        for (i in 0 until n) {
            delay(100)
            emit(i)
        }
    }
}

fun main() = runBlocking {
    log.info("Start runBlocking")
    val result = range(5)
        .take(3)
        .map { it * 2 }
        .toList()
    log.info("result: {}", result)
    log.info("Finish runBlocking")
}