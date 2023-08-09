package com.campusgram.article.publisher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreatedEvent implements ArticleEvent {
    private String articleId;
    private String creatorUserId;
}
