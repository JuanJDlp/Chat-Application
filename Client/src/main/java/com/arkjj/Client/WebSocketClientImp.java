package com.arkjj.Client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.arkjj.POJOs.MessagePojo;

public class WebSocketClientImp {

    static StompSession stompSession;

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

        var messagePojo = new MessagePojo() {
            {
                setContent(message);
                setSender("Juan"); // TODO: Register sender from GUI
                setReceivers(["*"]); // TODO: Set receivers from GUI, "*" doesn't exclude sender
            }
        };

        stompSession.send("/app/hello", messagePojo);
    }
}
