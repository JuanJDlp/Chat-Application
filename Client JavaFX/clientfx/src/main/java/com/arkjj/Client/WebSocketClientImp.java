package com.arkjj.Client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.arkjj.ChatWindowController;
import com.arkjj.model.MessagePojo;
import com.arkjj.model.User;
import com.google.gson.Gson;

public class WebSocketClientImp {

    static StompSession stompSession;
    private static MessagePojo messagePojo = new MessagePojo();
    private static final Gson gson = new Gson();
    private String username;
    private String receiverID;

    public WebSocketClientImp(ChatWindowController windowController, String username) {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        this.username = username;
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            stompSession = stompClient
                    .connectAsync("ws://localhost:8080/chat", new MyStompSessionHandler(windowController))
                    .get();
            User user = new User(stompSession.getSessionId(), username);
            String userJSON = gson.toJson(user);
            stompSession.send("/app/chat.addUser", new MessagePojo(userJSON));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;

    }

    public void sendMessage(String destination, String message) {

        messagePojo.setContent(message);
        messagePojo.setType(MessagePojo.Type.CHAT);
        messagePojo.setSenderID(stompSession.getSessionId());
        stompSession.send(destination, messagePojo);

    }

    public void sendPrivateMessage(String destination, String message) {
        messagePojo.setContent(message);
        messagePojo.setType(MessagePojo.Type.CHAT);
        messagePojo.setSenderID(stompSession.getSessionId());
        messagePojo.setReceiverID(receiverID);
        messagePojo.setSenderUsername(username);
        stompSession.send(destination, messagePojo);
    }
}
