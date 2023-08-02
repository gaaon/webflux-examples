package com.grizz.wooman.coroutine.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chat")
class ChatDocument(
    @Id
    var id: ObjectId?,
    var from: String,
    var to: String,
    var message: String,
) {

    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    constructor(from: String, to: String, message: String) : this(null, from, to, message)

    @PersistenceCreator
    constructor(
        id: ObjectId,
        from: String,
        to: String,
        message: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ): this(id, from, to, message) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }

    fun withId(id: ObjectId): ChatDocument {
        return ChatDocument(id, from, to, message)
    }
}