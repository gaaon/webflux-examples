package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

private val log = kLogger()
private class GreetingContext(
    private val greeting: String,
) : AbstractCoroutineContextElement(GreetingContext) {
    public companion object Key :
        CoroutineContext.Key<GreetingContext>

    fun greet() {
        log.info(greeting)
    }
}

fun main() {
    runBlocking {
        val context = GreetingContext("Hello")

        launch(context) {
            coroutineContext[GreetingContext]?.greet()

            val newContext = GreetingContext("Hola")
            launch(newContext) {
                coroutineContext[GreetingContext]?.greet()

                launch {
                    coroutineContext[GreetingContext]?.greet()
                }
            }
        }

        val job = CoroutineScope(Dispatchers.IO + context).launch {
            coroutineContext[GreetingContext]?.greet()

            launch {
                coroutineContext[GreetingContext]?.greet()

                launch {
                    coroutineContext[GreetingContext]?.greet()
                }
            }
        }

        job.join()
    }
}