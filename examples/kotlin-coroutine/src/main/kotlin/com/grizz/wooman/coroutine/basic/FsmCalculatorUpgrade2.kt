package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

private val log = kLogger()

class FsmCalculatorUpgrade2 {
    private class CustomContinuation(
        val completion: Continuation<Int>,
        val that: FsmCalculatorUpgrade2,
    ) : Continuation<Int> {
        var result: Any? = null
        var label: Int = 0

        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            this.result = result.getOrThrow()
            that.calculate(0, this)
        }

        fun complete(value: Int) {
            completion.resume(value)
        }
    }

    fun calculate(
        initialValue: Int,
        continuation: Continuation<Int>,
    ) {
        val cont = if (continuation is CustomContinuation) {
            continuation
        } else {
            CustomContinuation(continuation, this)
        }

        when (cont.label) {
            0 -> {
                cont.label = 1
                initialize(initialValue, cont)
            }

            1 -> {
                val initialized = cont.result as Int
                cont.label = 2
                addOne(initialized, cont)
            }

            2 -> {
                val added = cont.result as Int
                cont.label = 3
                multiplyTwo(added, cont)
            }

            3 -> {
                val multiplied = cont.result as Int
                cont.complete(multiplied)
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
    val completion = Continuation<Int>(EmptyCoroutineContext) {
        log.info("Result: {}", it)
    }
    FsmCalculatorUpgrade2().calculate(5, completion)
}