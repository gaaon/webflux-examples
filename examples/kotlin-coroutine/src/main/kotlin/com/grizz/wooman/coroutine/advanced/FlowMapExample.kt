package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val numbers = flowOf(1, 2, 3, 4, 5)

        numbers.map { it * 2 }.collect {
            log.info("value1: {}", it)
        }

        numbers.mapNotNull {
            if (it % 2 == 0) null else it
        }.collect {
            log.info("value2: {}", it)
        }
    }
}