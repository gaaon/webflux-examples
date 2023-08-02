package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val numbers = flowOf(1, 2, 3, 4, 5)

        val firstNumber = numbers.first()
        log.info("first: {}", firstNumber)

        try {
            val singleNumber = numbers.single()
            log.info("single: {}", singleNumber)
        } catch (e: Exception) {
            log.error("single error1: {}", e.message)
        }

        try {
            val singleNumber = emptyFlow<Int>().single()
            log.info("single: {}", singleNumber)
        } catch (e: Exception) {
            log.error("single error2: {}", e.message)
        }
    }
}