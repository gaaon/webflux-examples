package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger

private val log = kLogger()
object NormalSeparatedCalculator {
    fun calculate(initialValue: Int) {
        var result = initialize(initialValue)
        result = addOne(result)
        result = multiplyTwo(result)
        log.info("Result: {}", result)
    }

    private fun initialize(value: Int): Int {
        return value
    }

    private fun addOne(value: Int): Int {
        return value + 1
    }

    private fun multiplyTwo(value: Int): Int {
        return value * 2
    }
}

fun main() {
    NormalSeparatedCalculator.calculate(5)
}