package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

private val log = kLogger()
class ContinuationCalculator {
    fun calculate(initialValue: Int, continuation: Continuation<Int>) {
        class ContinuationImpl(
            var completion: Continuation<Int>,
        ): Continuation<Any> {
            var label: Int = 0
            var result: Any? = null

            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Any>) {
                this.result = result.getOrThrow()
                this@ContinuationCalculator.calculate(initialValue, this)
            }

            fun complete(value: Int) {
                completion.resume(value)
            }
        }

        val c = if (continuation is ContinuationImpl) {
            continuation
        } else {
            ContinuationImpl(completion = continuation)
        }

        when (c.label) {
            0 -> {
                c.label = 1
                initialize(initialValue, c)
            }
            1 -> {
                val initial = c.result as Int
                c.label = 2
                addOne(initial, c)
            }
            2 -> {
                val added = c.result as Int
                c.label = 3
                multiplyTwo(added, c)
            }
            3 -> {
                val multiplied = c.result as Int
                c.complete(multiplied)
            }
        }
    }

    private fun initialize(
        value: Int,
        cont: Continuation<Int>,
    ) {
        log.info("Initial")
        cont.resume(value)
    }

    private fun addOne(
        value: Int,
        cont: Continuation<Int>,
    ) {
        log.info("Add one")
        cont.resume(value + 1)
    }

    private fun multiplyTwo(
        value: Int,
        cont: Continuation<Int>) {
        log.info("Multiply two")
        cont.resume(value * 2)
    }
}

fun main() {
    val calculator = ContinuationCalculator()
    val continuation = Continuation<Int>(EmptyCoroutineContext) {
        log.info("Result: {}", it)
    }

    calculator.calculate(5, continuation)
}