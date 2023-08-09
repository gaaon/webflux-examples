package com.campusgram.article.controller;

import com.campusgram.article.entity.Article;
import com.campusgram.article.entity.ArticleThumbnail;
import com.campusgram.article.entity.ArticleThumbnailIdOnly;
import com.campusgram.article.usecase.CreateArticleUsecase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@AutoConfigureWebTestClient
@WebFluxTest(
        controllers = {ArticleController.class}
)
class ArticleControllerTest {
    @MockBean
    CreateArticleUsecase mockCreateArticleUsecase;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoad() {
    }

    @Test
    void happyCase() {
        // given
        var newArticleId = "abcd";
        var title = "title1";
        var content = "content1";
        var creatorUserId = "4321";
        var thumbnailImageIds = List.of("1", "2", "3");
        var thumbnails = thumbnailImageIds.stream()
                .map(it -> (ArticleThumbnail) new ArticleThumbnailIdOnly(it))
                .collect(Collectors.toList());

        var articleToReturn = new Article(
                newArticleId, title, content, thumbnails, creatorUserId
        );
        var input = new CreateArticleUsecase.Input(
                title, content, thumbnailImageIds, creatorUserId
        );
        when(mockCreateArticleUsecase.execute(eq(input)))
                .thenReturn(Mono.just(articleToReturn));

        // when
        var body = "{\"title\": \"" + title + "\"," +
                "\"content\": \"" + content + "\"," +
                "\"thumbnailImageIds\": [\"1\", \"2\", \"3\"]}";

        var result = webTestClient.post()
                .uri("/api/v1/articles")
                .bodyValue(body)
                .header("Content-Type", "application/json")
                .header("X-User-Id", creatorUserId)
                .exchange()
                .expectBody()
                .jsonPath("$.id").isEqualTo(newArticleId)
                .jsonPath("$.title").isEqualTo(title)
                .jsonPath("$.content").isEqualTo(content)
                .jsonPath("$.creatorId").isEqualTo(creatorUserId)
                .returnResult();

        // then
        log.info(new String(result.getResponseBody()));
    }
}