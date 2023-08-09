package com.campusgram.user.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreatedEvent {
    private String articleId;
    private String creatorUserId;
}
