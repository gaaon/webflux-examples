package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val numbers = flowOf(1, 2, 3, 4, 5)

        val reduced = numbers.reduce { accumulator, value ->
            accumulator + value
        }
        log.info("reduced: {}", reduced)

        val folded = numbers.fold(10) { accumulator, value ->
            accumulator + value
        }
        log.info("folded: {}", folded)
    }
}