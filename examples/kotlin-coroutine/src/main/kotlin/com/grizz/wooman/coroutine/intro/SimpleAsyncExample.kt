package com.grizz.wooman.coroutine.intro

import com.grizz.wooman.coroutine.help.kLogger
import reactor.core.publisher.Mono

private val log = kLogger()
fun main() {
    val personRepository = PersonReactorRepository
    val articleRepository = ArticleFutureRepository

    personRepository.findPersonByName("taewoo")
        .flatMap { person ->
            val future = articleRepository.findArticleById(person.id)

            Mono.fromFuture(future)
                .map { article -> person to article }
        }.subscribe { (person, article) ->
            log.info("person: {}, article: {}", person, article)
        }
}