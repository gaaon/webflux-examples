package com.campusgram.article.repository.mongo.repository;

import com.campusgram.article.repository.mongo.document.ArticleDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ArticleMongoRepository extends ReactiveMongoRepository<ArticleDocument, ObjectId> {
}
