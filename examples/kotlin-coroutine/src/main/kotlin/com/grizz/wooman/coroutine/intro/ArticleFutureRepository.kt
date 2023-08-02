package com.grizz.wooman.coroutine.intro

import java.util.concurrent.CompletableFuture

object ArticleFutureRepository {
    fun findArticleById(id: Long): CompletableFuture<Article> {
        return CompletableFuture.completedFuture(Article(id, "article $id"))
    }
}