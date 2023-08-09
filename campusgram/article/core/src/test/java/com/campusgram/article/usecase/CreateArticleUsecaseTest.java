package com.campusgram.article.usecase;

import com.campusgram.article.entity.Article;
import com.campusgram.article.entity.ArticleThumbnail;
import com.campusgram.article.entity.ArticleThumbnailIdOnly;
import com.campusgram.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateArticleUsecaseTest {
    @InjectMocks
    CreateArticleUsecase createArticleUsecase;

    @Mock
    ArticleRepository mockArticleRepository;

    @Test
    void happyCase() {
        // given
        var newArticleId = "1";
        var title = "title1";
        var content = "content1";
        var creatorId = "4321";
        var thumbnailImageIds = List.of("1", "2", "3");

        var savedArticle = new Article(
                newArticleId,
                title,
                content,
                thumbnailImageIds.stream()
                        .map(ArticleThumbnailIdOnly::new)
                        .collect(Collectors.toList()),
                creatorId
        );
        when(mockArticleRepository.save(any()))
                .thenReturn(Mono.just(savedArticle));

        // when
        var input = new CreateArticleUsecase.Input(
                title, content, thumbnailImageIds, creatorId
        );
        var result = createArticleUsecase.execute(input);

        // then
        StepVerifier.create(result)
                .assertNext(article -> {
                    assertEquals(newArticleId, article.getId());
                    assertEquals(title, article.getTitle());
                    assertEquals(content, article.getContent());
                    assertEquals(creatorId, article.getCreatorId());

                    var actualImageIds = article.getThumbnails()
                                    .stream().map(ArticleThumbnail::getId)
                                    .collect(Collectors.toList());
                    assertEquals(thumbnailImageIds, actualImageIds);
                })
                .verifyComplete();
    }
}