package com.grizz.wooman.coroutine.help

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

inline fun kLogger(): Logger {
    return logger<GlobalLogger>()
}

object GlobalLogger