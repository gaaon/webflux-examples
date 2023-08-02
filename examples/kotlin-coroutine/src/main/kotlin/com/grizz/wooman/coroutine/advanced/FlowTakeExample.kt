package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val numbers = flow<Int> {
            emit(1)
            log.info("executed")
            emit(2)
            log.info("not executed")
            emit(3)
        }

        numbers.take(2).collect {
            log.info("value1: {}", it)
        }
    }
}