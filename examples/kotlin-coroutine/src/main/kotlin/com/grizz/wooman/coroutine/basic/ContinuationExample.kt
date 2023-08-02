package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private val log = kLogger()

fun main() {
    var visited = false
    val continuation = object: Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            if (visited) {
                log.info("Result: {}", result)
            } else {
                log.info("Visit now")
                visited = true
            }
        }
    }

    continuation.resume(10)
    continuation.resume(10)
    continuation.resumeWithException(
        IllegalStateException()
    )
}