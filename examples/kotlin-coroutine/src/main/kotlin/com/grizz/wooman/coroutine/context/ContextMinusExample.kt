package com.grizz.wooman.coroutine.context

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

private val log = kLogger()
@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val context1 = CoroutineName("custom name") +
            Dispatchers.IO +
            Job()
    log.info("context: {}, class: {}", context1,
        context1.javaClass.simpleName)

    val context2 = context1.minusKey(Job)
    log.info("context2: {}, class: {}", context2,
        context2.javaClass.simpleName)

    val context3 = context2.minusKey(Job)
    log.info("context3: {}, class: {}", context3,
        context3.javaClass.simpleName)

    val context4 = context3.minusKey(CoroutineDispatcher)
    log.info("context4: {}, class: {}", context4,
        context4.javaClass.simpleName)

    val context5 = context4.minusKey(CoroutineName)
    log.info("context5: {}, class: {}", context5,
        context5.javaClass.simpleName)
}