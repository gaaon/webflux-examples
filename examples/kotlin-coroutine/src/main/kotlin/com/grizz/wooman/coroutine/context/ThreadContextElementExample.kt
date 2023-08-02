package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()
fun main() {
    runBlocking {
        val greeting = ThreadLocal<String>()
        greeting.set("hello")

        launch(Dispatchers.IO) {
            log.info("greeting1: {}", greeting.get())
        }

        val aContext = Dispatchers.IO +
                greeting.asContextElement()
        launch(aContext) {
            log.info("greeting2: {}", greeting.get())
        }

        val bContext = Dispatchers.Default +
                greeting.asContextElement("hoi")
        launch(bContext) {
            log.info("greeting3: {}", greeting.get())

            launch {
                log.info("greeting4: {}", greeting.get())
            }
        }
    }
}