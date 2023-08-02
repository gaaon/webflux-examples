package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

private val log = kLogger()
// (x + 1) * 2
class FsmCalculatorUpgrade1 {
    data class Shared(
        var result: Any? = null,
        var label: Int = 0,
    )

    fun calculate(initialValue: Int,
                  shared: Shared? = null) {
        val current = shared ?: Shared()

        val cont = object: Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                current.result = result.getOrThrow()
                this@FsmCalculatorUpgrade1.calculate(initialValue, current)
            }
        }

        when (current.label) {
            0 -> {
                current.label = 1
                initialize(initialValue, cont)
            }
            1 -> {
                val initialized = current.result as Int
                current.label = 2
                addOne(initialized, cont)
            }
            2 -> {
                val added = current.result as Int
                current.label = 3
                multiplyTwo(added, cont)
            }
            3 -> {
                val multiplied = current.result as Int
                log.info("Result: {}", multiplied)
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
    FsmCalculatorUpgrade1().calculate(5)
}