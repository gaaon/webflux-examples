package com.grizz.wooman.coroutine.repository

import com.grizz.wooman.coroutine.entity.ChatDocument
import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineSortingRepository

interface ChatMongoCoroutineRepository :
    CoroutineSortingRepository<ChatDocument, ObjectId>