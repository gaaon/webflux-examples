package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger

private val log = kLogger()
object NormalCalculator {
    fun calculate(initialValue: Int) {
        var result = initialValue
        result += 1
        result *= 2
        log.info("Result: {}", result)
    }
}

fun main() {
    NormalCalculator.calculate(5)
}