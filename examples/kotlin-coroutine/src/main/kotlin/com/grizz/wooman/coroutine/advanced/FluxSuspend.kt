package com.grizz.wooman.coroutine.advanced

import kotlinx.coroutines.delay

private suspend fun square(x: Int): Int {
    delay(10)
    return x * x
}

fun main() {
//    Flux.create<Int> {
//        it.next(square(10))
//    }.subscribe()
}