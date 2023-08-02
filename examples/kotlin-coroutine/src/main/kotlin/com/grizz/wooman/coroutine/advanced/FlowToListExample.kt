package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking

private val log = kLogger()

fun main() {
    runBlocking {
        val numbers = flowOf(1, 2, 3, 4, 5)

        val numberList = numbers.toList()
        log.info("list: {}", numberList)
        log.info("list type: {}", numberList.javaClass.name)

        val numberSet = numbers.toSet()
        log.info("set: {}", numberSet)
        log.info("set type: {}", numberSet.javaClass.name)
    }
}