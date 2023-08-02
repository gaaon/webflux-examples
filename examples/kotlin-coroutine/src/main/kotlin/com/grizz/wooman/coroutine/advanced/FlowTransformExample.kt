package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val numbers = flowOf(1, 2, 3, 4, 5)

        numbers.transform { item ->
            emit(item * item)
        }.collect {
            log.info("value1: {}", it)
        }

        numbers.transform { item ->
            if (item % 2 == 0) {
                emit(item * item)
                emit(item * item * item)
            }
        }.collect {
            log.info("value2: {}", it)
        }
    }
}