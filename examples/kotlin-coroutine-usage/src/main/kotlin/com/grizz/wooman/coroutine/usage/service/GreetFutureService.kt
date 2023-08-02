package com.grizz.wooman.coroutine.usage.service

import java.util.concurrent.CompletableFuture

interface GreetFutureService {
    fun findGreet(): CompletableFuture<String>
}