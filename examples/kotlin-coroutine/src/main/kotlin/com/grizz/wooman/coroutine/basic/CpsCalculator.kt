package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger

private val log = kLogger()
object CpsCalculator {
    fun calculate(initialValue: Int, continuation: (Int) -> Unit) {
        initialize(initialValue) { initial ->
            addOne(initial) { added ->
                multiplyTwo(added) { multiplied ->
                    continuation(multiplied)
                }
            }
        }
    }

    private fun initialize(value: Int, continuation: (Int) -> Unit) {
        log.info("Initial")
        continuation(value)
    }

    private fun addOne(value: Int, continuation: (Int) -> Unit) {
        log.info("Add one")
        continuation(value + 1)
    }

    private fun multiplyTwo(value: Int, continuation: (Int) -> Unit) {
        log.info("Multiply two")
        continuation(value * 2)
    }
}

fun main() {
    CpsCalculator.calculate(5) { result ->
        log.info("Result: {}", result)
    }
}