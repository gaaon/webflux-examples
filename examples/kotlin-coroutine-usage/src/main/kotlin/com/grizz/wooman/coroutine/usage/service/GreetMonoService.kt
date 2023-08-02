package com.grizz.wooman.coroutine.usage.service

import reactor.core.publisher.Mono

interface GreetMonoService {
    fun findGreet(): Mono<String>
}