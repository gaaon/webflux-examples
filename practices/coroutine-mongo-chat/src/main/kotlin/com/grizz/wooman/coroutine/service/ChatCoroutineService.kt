package com.grizz.wooman.coroutine.service

import com.grizz.wooman.coroutine.entity.ChatDocument
import com.grizz.wooman.coroutine.helper.logger
import com.grizz.wooman.coroutine.repository.ChatMongoCoroutineRepository
import com.mongodb.client.model.changestream.OperationType
import kotlinx.coroutines.runBlocking
import org.springframework.data.mongodb.core.ChangeStreamEvent
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PostConstruct

@Service
class ChatCoroutineService(
    private val chatMongoRepository: ChatMongoCoroutineRepository,
    private val mongoTemplate: ReactiveMongoTemplate
) {
    companion object {
        private val chatSinkMap: MutableMap<String, Sinks.Many<Chat>> = ConcurrentHashMap()
    }

    private val log = logger<ChatCoroutineService>()

    @PostConstruct
    fun setup() {
        mongoTemplate.changeStream(ChatDocument::class.java)
            .listen()
            .doOnNext { item: ChangeStreamEvent<ChatDocument> ->
                val target = item.body
                val operationType = item.operationType
                log.info("target: {}", target)
                log.info("type: {}", operationType)
                if (target != null && operationType == OperationType.INSERT) {
                    val from = target.from
                    val to = target.to
                    val message = target.message
                    doSend(from, to, message)
                }
            }.subscribe()
    }

    fun register(iam: String): Flux<Chat> {
        val sink = Sinks.many().unicast().onBackpressureBuffer<Chat>()
        chatSinkMap[iam] = sink
        return sink.asFlux()
    }

    suspend fun sendChat(from: String, to: String, message: String) {
        log.info("from: {}, to: {}, message: {}", from, to, message)
        val documentToSave = ChatDocument(from, to, message)

        chatMongoRepository.save(documentToSave)
    }

    private fun doSend(from: String, to: String, message: String) {
        val sink = chatSinkMap[to]
        if (sink == null) {
            val my = chatSinkMap[from]!!
            my.tryEmitNext(Chat("대화 상대가 없습니다", "system"))
            return
        }
        sink.tryEmitNext(Chat(message, from))
    }
}