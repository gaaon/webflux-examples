package com.campusgram.article.controller;

import com.campusgram.article.entity.Article;
import com.campusgram.article.entity.ArticleThumbnail;
import com.campusgram.article.usecase.CreateArticleUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final CreateArticleUsecase createArticleUsecase;

    @PostMapping
    Mono<ArticleResponse> createArticle(
        @RequestBody CreateArticleRequest createArticleRequest,
        @RequestHeader("X-User-Id") String userId
    ) {
        var input = new CreateArticleUsecase.Input(
                createArticleRequest.getTitle(),
                createArticleRequest.getContent(),
                createArticleRequest.getThumbnailImageIds(),
                userId
        );

        return createArticleUsecase.execute(input)
                .map(this::fromEntity);
    }

    private ArticleResponse fromEntity(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatorId(),
                article.getThumbnails().stream()
                        .map(this::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    private ThumbnailResponse fromEntity(ArticleThumbnail thumbnail) {
        return new ThumbnailResponse(
                thumbnail.getId(),
                thumbnail.getUrl(),
                thumbnail.getWidth(),
                thumbnail.getHeight()
        );
    }
}
