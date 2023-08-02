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
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val numbers = flowOf(1, 2, 3, 4, 5)

        numbers.filter { it % 2 == 0 }.collect {
            log.info("value1: {}", it)
        }

        numbers.filterNot { it % 2 == 0 }.collect {
            log.info("value2: {}", it)
        }

        val objects = flowOf(10, "20", emptyList<String>(), null)

        objects.filterIsInstance<Int>().collect {
            log.info("value3: {}", it)
        }

        objects.filterNotNull().collect {
            log.info("value4: {}", it)
        }
    }
}