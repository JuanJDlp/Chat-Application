package com.arkjj.Client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.arkjj.POJOs.MessagePojo;

public class WebSocketClientImp {

    static StompSession stompSession;
    private static MessagePojo messagePojo = new MessagePojo();

    public WebSocketClientImp() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            stompSession = stompClient.connectAsync("ws://localhost:8080/chat", new MyStompSessionHandler()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {

        messagePojo.setContent(message);
        messagePojo.setSender("Juan");
        stompSession.send("/app/hello", messagePojo);

    }
}
