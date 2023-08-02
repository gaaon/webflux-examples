package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

private val log = kLogger()

private suspend fun square(x: Int): Int {
    delay(10)
    return x * x
}

fun main() {
    runBlocking {
        val squareFlow: Flow<Int> = flow {
            withContext(Dispatchers.IO) {
                emit(square(10))
                emit(square(20))
                emit(square(30))
            }
        }

        squareFlow.collect {
            log.info("value: {}", it)
        }
    }
}