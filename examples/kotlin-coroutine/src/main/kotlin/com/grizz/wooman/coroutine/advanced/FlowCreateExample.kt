package com.grizz.wooman.coroutine.advanced

import com.grizz.wooman.coroutine.help.kLogger
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.coroutines.asFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.runBlocking
import reactor.core.publisher.Flux

private val log = kLogger()

fun main() {
    runBlocking {
        val numbers = flowOf(1, 2, 3)
        numbers.collect {
            log.info("value1: {}", it)
        }


        val numbers2 = listOf(1, 2, 3).asFlow()
        numbers2.collect {
            log.info("value2: {}", it)
        }

        val numbers3 = (1 .. 3).asFlow()
        numbers3.collect {
            log.info("value3: {}", it)
        }

        val numbers4 = Flux.just(1, 2, 3).asFlow()
        numbers4.collect {
            log.info("value4: {}", it)
        }

        val numbers5 = Multi.createFrom().range(0, 3).asFlow()
        numbers5.collect {
            log.info("value5: {}", it)
        }
    }
}