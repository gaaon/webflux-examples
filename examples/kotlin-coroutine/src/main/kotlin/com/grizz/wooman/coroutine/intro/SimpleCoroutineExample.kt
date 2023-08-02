package com.grizz.wooman.coroutine.intro

import com.grizz.wooman.coroutine.help.kLogger
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking

private val log = kLogger()
suspend fun main() = runBlocking {
    val personRepository = PersonReactorRepository
    val articleRepository = ArticleFutureRepository

    val person = personRepository.findPersonByName("taewoo")
        .awaitSingle()

    val article = articleRepository.findArticleById(person.id)
        .await()

    log.info("person: {}, article: {}", person, article)
}