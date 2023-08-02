package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger

private val log = kLogger()
object CallbackExample {
    fun handleButtonClicked(
        callback: () -> Unit,
        continuation: (count: Int) -> Unit
    ) {
        var count = 0

        for (i in 0 until 5) {
            // 버튼이 눌렸다고 가정
            count++
            callback()
        }

        continuation(count)
    }
}

fun main() {
    CallbackExample.handleButtonClicked(
        callback = {
            log.info("Button clicked")
        },
        continuation = { count ->
            log.info("Clicked count: $count")
        }
    )
}