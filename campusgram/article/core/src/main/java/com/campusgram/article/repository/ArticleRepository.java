package com.campusgram.article.repository;

import com.campusgram.article.entity.Article;
import reactor.core.publisher.Mono;

public interface ArticleRepository {
    Mono<Article> save(Article article);
}
