package com.grizz.wooman.test.app.repository.chat;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "chat")
public class ChatDocument {
    @Id
    private final ObjectId id;
    private final String from;
    private final String to;
    private final String message;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public ChatDocument(String from, String to, String message) {
        this(null, from, to, message, null, null);
    }

    @PersistenceCreator
    public ChatDocument(ObjectId id, String from, String to, String message, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ChatDocument withId(ObjectId id) {
        return new ChatDocument(id, this.from, this.to, this.message, this.createdAt, this.updatedAt);
    }
}
