package com.grizz.wooman.coroutine.usage.controller

import com.grizz.wooman.coroutine.help.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.coroutineContext

@RestController
@RequestMapping("/greet")
class GreetController {
    private val log = logger<GreetController>()

    private suspend fun greeting(): String {
        return "hello"
    }

    @GetMapping
    suspend fun greet(): String {
        log.info("context: {}", coroutineContext)
        log.info("thread: {}", Thread.currentThread().name)
        return greeting()
    }
}