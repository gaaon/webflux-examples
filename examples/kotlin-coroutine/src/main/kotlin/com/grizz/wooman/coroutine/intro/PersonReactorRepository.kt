package com.grizz.wooman.coroutine.intro

import reactor.core.publisher.Mono

object PersonReactorRepository {
    fun findPersonByName(name: String): Mono<Person> {
        val id = (Math.random() * 10000).toLong()
        return Mono.just(Person(id, name))
    }
}