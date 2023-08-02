package com.grizz.wooman.coroutine.basic

import com.grizz.wooman.coroutine.help.kLogger

private val log = kLogger()
class FsmExample {
    fun execute(label: Int = 0) {
        var nextLabel: Int? = null

        when (label) {
            0 -> {
                log.info("Initial")
                nextLabel = 1
            }
            1 -> {
                log.info("State1")
                nextLabel = 2
            }
            2 -> {
                log.info("State2")
                nextLabel = 3
            }
            3 -> {
                log.info("End")
            }
        }
        // transition
        if (nextLabel != null) {
            this.execute(nextLabel)
        }
    }
}

fun main() {
    val fsmExample = FsmExample()
    fsmExample.execute()
}