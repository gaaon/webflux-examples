package com.grizz.wooman.websocket.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    private final ChatService chatService;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String iam = (String) session.getAttributes().get("iam");

        Flux<Chat> chatFlux = chatService.register(iam);
        chatService.sendChat(iam,
                new Chat(iam + "님 채팅방에 오신 것을 환영합니다", "system"));

        session.receive()
                .doOnNext(webSocketMessage -> {
                    String payload = webSocketMessage.getPayloadAsText();

                    String[] splits = payload.split(":");
                    String to = splits[0].trim();
                    String message = splits[1].trim();

                    boolean result = chatService.sendChat(to, new Chat(message, iam));
                    if (!result) {
                        chatService.sendChat(iam, new Chat("대화 상대가 없습니다", "system"));
                    }
                }).subscribe();

        return session.send(chatFlux
                .map(chat -> session.textMessage(chat.getFrom() + ": " + chat.getMessage()))
        );
    }
}
