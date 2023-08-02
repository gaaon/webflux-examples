package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()
fun main() {
    val context = EmptyCoroutineContext
    log.info("context: {}", context)

    val element1 = CoroutineName("custom name")
    val context2 = context + element1
    log.info("context2: {}, class: {}", context2,
        context2.javaClass.simpleName)

    val element2 = CoroutineName("custom name2")
    val context3 = context2 + element2
    log.info("context3: {}, class: {}", context3,
        context3.javaClass.simpleName)

    val element3 = Dispatchers.IO
    val context4 = context3 + element3
    log.info("context4: {}, class: {}", context4,
        context4.javaClass.simpleName)

    val element4 = Job()
    val context5 = context4 + element4
    log.info("context5: {}, class: {}", context5,
        context5.javaClass.simpleName)
}