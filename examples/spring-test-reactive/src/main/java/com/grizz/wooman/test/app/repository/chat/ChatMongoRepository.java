package com.grizz.wooman.test.app.repository.chat;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatMongoRepository
        extends ReactiveMongoRepository<ChatDocument, ObjectId> {
    Flux<ChatDocument> findAllByFrom(String from);
}
