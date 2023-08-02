package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

private val log = kLogger()
// (x + 1) * 2
object FsmCalculator {
    data class Shared(
        var result: Int = 0,
        var label: Int = 0,
    )

    fun calculate(initialValue: Int,
                  shared: Shared? = null) {
        val current = shared ?: Shared()

        val simpleCont = Continuation<Int>(EmptyCoroutineContext) {
            this.calculate(initialValue, current)
        }

        when (current.label) {
            0 -> {
                current.label = 1
                initialize(initialValue, simpleCont)
                current.result = initialValue
            }
            1 -> {
                current.result += 1
                current.label = 2
            }
            2 -> {
                current.result *= 2
                current.label = 3
            }
            3 -> {
                log.info("Result: {}", current.result)
                return
            }
        }
    }

    private fun initialize(value: Int, cont: Continuation<Int>) {
        log.info("Initial")
        cont.resume(value)
    }

    private fun addOne(value: Int, cont: Continuation<Int>) {
        log.info("Add one")
        cont.resume(value + 1)
    }

    private fun multiplyTwo(value: Int, cont: Continuation<Int>) {
        log.info("Multiply two")
        cont.resume(value * 2)
    }
}

fun main() {
    FsmCalculator.calculate(5)
}