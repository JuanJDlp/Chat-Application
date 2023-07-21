package com.arkjj.Client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.arkjj.ChatWindowController;
import com.arkjj.model.MessagePojo;

public class WebSocketClientImp {

    static StompSession stompSession;
    private static MessagePojo messagePojo = new MessagePojo();

    public WebSocketClientImp(ChatWindowController windowController, String username) {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            stompSession = stompClient
                    .connectAsync("ws://localhost:8080/chat", new MyStompSessionHandler(windowController))
                    .get();
            stompSession.send("/app/chat.addUser", new MessagePojo(null, stompSession.getSessionId(), username));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {

        messagePojo.setContent(message);

        messagePojo.setSenderID(stompSession.getSessionId());
        stompSession.send("/app/chat.sendMessage", messagePojo);

    }
}
